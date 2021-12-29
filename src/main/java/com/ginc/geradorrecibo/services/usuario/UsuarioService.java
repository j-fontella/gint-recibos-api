package com.ginc.geradorrecibo.services.usuario;

import com.ginc.geradorrecibo.dtos.request.usuario.UsuarioLoginRequestDTO;
import com.ginc.geradorrecibo.dtos.request.usuario.UsuarioRecuperarPasswordRequestDTO;
import com.ginc.geradorrecibo.dtos.request.usuario.UsuarioRequestDTO;
import com.ginc.geradorrecibo.dtos.request.usuario.UsuarioUpdateRequestDTO;
import com.ginc.geradorrecibo.dtos.response.login.UsuarioLoginResponseDTO;
import com.ginc.geradorrecibo.instances.PasswordEncoder;
import com.ginc.geradorrecibo.models.endereco.Endereco;
import com.ginc.geradorrecibo.models.login.Token;
import com.ginc.geradorrecibo.models.login.TokenReset;
import com.ginc.geradorrecibo.models.login.Usuario;
import com.ginc.geradorrecibo.repositorys.endereco.EnderecoRepository;
import com.ginc.geradorrecibo.repositorys.login.ResetUsuarioRepository;
import com.ginc.geradorrecibo.repositorys.login.UsuarioRepository;
import com.ginc.geradorrecibo.services.security.SecurityService;
import com.ginc.geradorrecibo.utils.Geral;
import com.ginc.geradorrecibo.utils.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UsuarioService{

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    Security security;

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    SecurityService securityService;

    @Autowired
    ResetUsuarioRepository resetUsuarioRepository;

    @Autowired
    private JavaMailSender mailSender;

    public ResponseEntity<?> recuperarSenha(UsuarioRecuperarPasswordRequestDTO usuarioRecuperarPasswordRequestDTO){
        Optional<TokenReset> tokenResetRequest = resetUsuarioRepository.findByHash(usuarioRecuperarPasswordRequestDTO.getHash());
        if(tokenResetRequest.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Chave de recuperação inválida");
        }
        TokenReset tokenReset = tokenResetRequest.get();
        if(!tokenReset.getUsuario().getEmail().equals(usuarioRecuperarPasswordRequestDTO.getEmail())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Chave de recuperação inválida");
        }
        if(LocalDateTime.now().isAfter(tokenReset.getDataExpiracao())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Chave de recuperação expirada");
        }
        resetUsuarioRepository.deleteByHash(tokenReset.getHash());
        Usuario user = tokenReset.getUsuario();
        user.setSenha(Geral.encriptarStringBCrypt(usuarioRecuperarPasswordRequestDTO.getSenha()));
        usuarioRepository.save(user);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> esqueciSenha(String email) throws NoSuchAlgorithmException {
        Optional<Usuario> user = usuarioRepository.findByEmail(email);
        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email não registrado.");
        }
        Optional<TokenReset> tokenReset = resetUsuarioRepository.findByFrkUsuario(user.get().getPrk());
        String hash;
        if(tokenReset.isPresent() && LocalDateTime.now().isAfter(tokenReset.get().getDataExpiracao())){
            resetUsuarioRepository.deleteByHash(tokenReset.get().getHash());
        }
        if(tokenReset.isPresent() && LocalDateTime.now().isBefore(tokenReset.get().getDataExpiracao())){
            hash = tokenReset.get().getHash();
        }else{
            TokenReset newTokenReset = new TokenReset();
            newTokenReset.setUsuario(user.get());
            newTokenReset.setDataExpiracao(LocalDateTime.now().plusHours(1));
            hash = Geral.encriptarStringSHA256( Geral.encriptarStringBCrypt(user.get().getEmail() + LocalDateTime.now().plusHours(3)));
            newTokenReset.setHash(hash);
            resetUsuarioRepository.save(newTokenReset);
        }

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("joaofontella11@gmail.com");
        msg.setTo(email);
        msg.setSubject("Recuperar senha GINC - Gerador de Recibos");
        msg.setText("Use o token abaixo para recuperar sua senha: \n" + hash);


        try {
            mailSender.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> getUsuarioPorPrk(Long prk, String token){
        try {
            securityService.validarToken(token, prk);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
        Optional<Usuario> user = usuarioRepository.findByPrk(prk);
        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não encontrado");
        }
       return new ResponseEntity<>(Geral.converterUsuarioResponse(user.get()),HttpStatus.OK);
    }

    public ResponseEntity<?> registrarUsuario(UsuarioRequestDTO usuarioRequestDTO){
        Optional<Usuario> user = usuarioRepository.findByEmail(usuarioRequestDTO.getEmail());
        if(user.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já registrado");
        }
        usuarioRequestDTO.setSenha(Geral.encriptarStringBCrypt(usuarioRequestDTO.getSenha()));
        Endereco end = enderecoRepository.save(usuarioRequestDTO.getEndereco());
        usuarioRepository.save(Geral.converterUsuarioRequest(usuarioRequestDTO, end));
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> atualizarUsuario(UsuarioUpdateRequestDTO usuarioRequestDTO, String token){
        try {
            securityService.validarToken(token, usuarioRequestDTO.getPrk());
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
        Optional<Usuario> user = usuarioRepository.findByEmail(usuarioRequestDTO.getEmail());
        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não localizado");
        }
        Usuario userAttemptingUpdate = user.get();
        BCryptPasswordEncoder bc = PasswordEncoder.getInstance();
        if(!bc.matches(usuarioRequestDTO.getSenha(), userAttemptingUpdate.getSenha())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Senha incorreta.");
        }
        String password = usuarioRequestDTO.getNovaSenha() == null || usuarioRequestDTO.getNovaSenha().isBlank() ? userAttemptingUpdate.getSenha() : Geral.encriptarStringBCrypt(usuarioRequestDTO.getNovaSenha());
        usuarioRequestDTO.setPrk(user.get().getPrk());
        usuarioRequestDTO.setSenha(password);
        usuarioRequestDTO.getEndereco().setPrk(user.get().getEndereco().getPrk());
        Endereco end = enderecoRepository.save(usuarioRequestDTO.getEndereco());
        usuarioRepository.save(Geral.converterUsuarioUpdate(usuarioRequestDTO, end));
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> logarUsuario(UsuarioLoginRequestDTO usuarioLoginRequestDTO) throws NoSuchAlgorithmException {
        Optional<Usuario> user = usuarioRepository.findByEmail(usuarioLoginRequestDTO.getEmail());
        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não registrado em nossa base de dados.");
        }
        Usuario userAttemptingConnect = user.get();
        BCryptPasswordEncoder bc = PasswordEncoder.getInstance();
        if(!bc.matches(usuarioLoginRequestDTO.getSenha(), userAttemptingConnect.getSenha())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Senha incorreta.");
        }
        Token token = security.gerarToken(userAttemptingConnect.getPrk());
        UsuarioLoginResponseDTO usuarioLoginResponseDTO = new UsuarioLoginResponseDTO();
        usuarioLoginResponseDTO.setPrk(userAttemptingConnect.getPrk());
        usuarioLoginResponseDTO.setToken(token.getHash());
        usuarioLoginResponseDTO.setNome(userAttemptingConnect.getNome());
        return new ResponseEntity<>(usuarioLoginResponseDTO,HttpStatus.OK);
    }

}
