package com.ginc.geradorrecibo.controllers.servico;

import com.ginc.geradorrecibo.dtos.request.paciente.PacienteRequestDTO;
import com.ginc.geradorrecibo.dtos.request.servico.ServicoRequestDTO;
import com.ginc.geradorrecibo.services.servico.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/servico")
public class ServicoController {

    @Autowired
    ServicoService servicoService;


    @PostMapping("/registrar")
    public ResponseEntity registrarServico(@Valid @RequestBody ServicoRequestDTO servicoRequestDTO, @RequestHeader(name = "token") String token) {
        return servicoService.registrarServico(servicoRequestDTO, token);
    }

    @PostMapping("/atualizar")
    public ResponseEntity atualizarServico(@Valid @RequestBody ServicoRequestDTO servicoRequestDTO, @RequestHeader(name = "token") String token) {
        return servicoService.atualizarServico(servicoRequestDTO, token);
    }

    @GetMapping("/getServicosPorUsuario")
    public ResponseEntity getPacientesPorUsuario(@RequestParam Long prk, @RequestHeader(name = "token") String token) {
        return servicoService.getServicosPorUsuario(prk, token);
    }

}
