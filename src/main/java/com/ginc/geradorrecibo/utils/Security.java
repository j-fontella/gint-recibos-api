package com.ginc.geradorrecibo.utils;

import com.ginc.geradorrecibo.exceptions.UserNotFoundException;
import com.ginc.geradorrecibo.models.login.Token;
import com.ginc.geradorrecibo.models.login.Usuario;
import com.ginc.geradorrecibo.repositorys.login.TokenRepository;
import com.ginc.geradorrecibo.repositorys.login.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Optional;

@EqualsAndHashCode()
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldNameConstants
@Service
public class Security {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TokenRepository tokenRepository;

   public static String gerarHash(Usuario user) throws NoSuchAlgorithmException {
        return Geral.encriptarStringBCrypt(Geral.encriptarStringSHA256(user.getEmail()) + Geral.encriptarStringSHA256(LocalDateTime.now().toString()));
    }

   public Boolean validarToken(String tokenPassado, Long prkUsuario){
       Optional<Token> tokenQuery = tokenRepository.findByFrkUsuario(prkUsuario);
       if(!tokenQuery.isPresent()){
           return false;
       }
       Token token = tokenQuery.get();


        return true;
    }

    public Token gerarToken(Long prk) throws NoSuchAlgorithmException {
        Optional<Usuario> user = this.usuarioRepository.findByPrk(prk);
        if(user.isEmpty()){
            throw new UserNotFoundException("Usuário inválido.");
        }
        Usuario usuario = user.get();
        this.clearUserToken(usuario.getPrk());
        Token token = new Token();
        token.setUsuario(usuario);
        token.setHash(Security.gerarHash(usuario));
        token.setDataExpiracao(LocalDateTime.now().plusHours(2));
        tokenRepository.save(token);
        return token;
    }

    public void clearUserToken(Long prk){
        tokenRepository.clearToken(prk);
    }
}
