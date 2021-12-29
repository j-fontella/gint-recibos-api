package com.ginc.geradorrecibo.services.servico;

import com.ginc.geradorrecibo.dtos.request.paciente.PacienteRequestDTO;
import com.ginc.geradorrecibo.dtos.request.servico.ServicoRequestDTO;
import com.ginc.geradorrecibo.models.paciente.Paciente;
import com.ginc.geradorrecibo.models.servico.Servico;
import com.ginc.geradorrecibo.repositorys.pacientes.PacienteRepository;
import com.ginc.geradorrecibo.repositorys.servico.ServicoRepository;
import com.ginc.geradorrecibo.services.security.SecurityService;
import com.ginc.geradorrecibo.utils.Geral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicoService {

    @Autowired
    SecurityService securityService;

    @Autowired
    ServicoRepository servicoRepository;

    public ResponseEntity<?> registrarServico(ServicoRequestDTO servicoRequestDTO, String token){
        try {
            securityService.validarToken(token, servicoRequestDTO.getFrkUsuario());
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
        Optional<Servico> servico = servicoRepository.findByNome(servicoRequestDTO.getNome(), servicoRequestDTO.getFrkUsuario());
        if(servico.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Serviço já registrado");
        }
        servicoRepository.save(Geral.converterServicoRequest(servicoRequestDTO));
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> getServicosPorUsuario(Long prk, String token){
        try {
            securityService.validarToken(token, prk);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
        Optional<List<Servico>> servicos = servicoRepository.getServicosPorUsuario(prk);
        if(servicos.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body("Nenhum serviço cadastrado para este usuário");
        }
        return ResponseEntity.status(HttpStatus.OK).body(Geral.converterServicoResponseToList(servicos.get()));
    }

    public ResponseEntity<?> atualizarServico(ServicoRequestDTO servicoRequestDTO, String token){
        try {
            securityService.validarToken(token, servicoRequestDTO.getFrkUsuario());
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
        Optional<Servico> servico = servicoRepository.findByPrk(servicoRequestDTO.getPrk());
        if(servico.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Serviço não encontrado");
        }
        Servico serv = servico.get();
        serv.setNome(servicoRequestDTO.getNome());
        servicoRepository.save(serv);
        return ResponseEntity.ok().build();
    }
}
