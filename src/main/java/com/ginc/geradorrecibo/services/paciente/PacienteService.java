package com.ginc.geradorrecibo.services.paciente;

import com.ginc.geradorrecibo.dtos.request.paciente.PacienteRequestDTO;
import com.ginc.geradorrecibo.dtos.response.paciente.PacienteResponseDTO;
import com.ginc.geradorrecibo.models.endereco.Endereco;
import com.ginc.geradorrecibo.models.login.Usuario;
import com.ginc.geradorrecibo.models.paciente.Paciente;
import com.ginc.geradorrecibo.repositorys.endereco.EnderecoRepository;
import com.ginc.geradorrecibo.repositorys.pacientes.PacienteRepository;
import com.ginc.geradorrecibo.services.security.SecurityService;
import com.ginc.geradorrecibo.utils.Geral;
import com.ginc.geradorrecibo.utils.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    SecurityService securityService;


    public ResponseEntity<?> registrarPaciente(PacienteRequestDTO pacienteRequestDTO, String token){
        try {
            securityService.validarToken(token, pacienteRequestDTO.getFrkUsuario());
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
        Optional<Paciente> paciente = pacienteRepository.findByCpfAndPrk(pacienteRequestDTO.getCpf(), pacienteRequestDTO.getFrkUsuario());
        if(paciente.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Paciente já registrado");
        }
        Endereco end = enderecoRepository.save(Geral.converterEnderecoRequest(pacienteRequestDTO.getEndereco()));
        pacienteRepository.save(Geral.converterPacienteRequest(pacienteRequestDTO, end));
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> atualizarPaciente(PacienteRequestDTO pacienteRequestDTO, String token){
        try {
            securityService.validarToken(token, pacienteRequestDTO.getFrkUsuario());
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
        System.out.println(pacienteRequestDTO.getCpf());
        System.out.println(pacienteRequestDTO.getFrkUsuario());
        Optional<Paciente> paciente = pacienteRepository.findByCpfAndPrk(pacienteRequestDTO.getCpf(), pacienteRequestDTO.getFrkUsuario());
        if(paciente.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Paciente não encontrado");
        }
        Endereco end = Geral.converterEnderecoRequest(pacienteRequestDTO.getEndereco());
        Paciente pac = paciente.get();
        pac.setNome(pacienteRequestDTO.getNome());
        end.setPrk(pac.getEndereco().getPrk());
        enderecoRepository.save(end);
        pacienteRepository.save(pac);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> getPacientesPorUsuario(Long prk, String token){
        try {
            securityService.validarToken(token, prk);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
        Optional<List<Paciente>> pacientes = pacienteRepository.findAllByPrk(prk);
        if(pacientes.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body("Nenhum paciente cadastrado para este usuário");
        }
        return ResponseEntity.status(HttpStatus.OK).body(Geral.converterPacienteResponseToList(pacientes.get()));
    }
}
