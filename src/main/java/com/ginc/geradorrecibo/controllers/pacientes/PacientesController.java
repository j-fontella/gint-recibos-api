package com.ginc.geradorrecibo.controllers.pacientes;


import com.ginc.geradorrecibo.dtos.request.paciente.PacienteRequestDTO;
import com.ginc.geradorrecibo.models.login.Token;
import com.ginc.geradorrecibo.repositorys.login.TokenRepository;
import com.ginc.geradorrecibo.services.paciente.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(value = "/paciente")
public class PacientesController {

    @Autowired
    PacienteService pacienteService;

    @Autowired
    TokenRepository tokenRepository;


    @PostMapping("/registrar")
    public ResponseEntity registrarPaciente(@Valid @RequestBody PacienteRequestDTO pacienteRequestDTO, @RequestHeader(name = "token") String token) {
        return pacienteService.registrarPaciente(pacienteRequestDTO, token);
    }

    @PostMapping("/atualizar")
    public ResponseEntity atualizarPaciente(@Valid @RequestBody PacienteRequestDTO pacienteRequestDTO, @RequestHeader(name = "token") String token) {
        return pacienteService.atualizarPaciente(pacienteRequestDTO, token);
    }

    @GetMapping("/getPacientesPorUsuario")
    public ResponseEntity getPacientesPorUsuario(@RequestParam Long prk, @RequestHeader(name = "token") String token) {
        return pacienteService.getPacientesPorUsuario(prk, token);
    }


}
