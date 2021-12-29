package com.ginc.geradorrecibo.controllers.recibo;

import com.ginc.geradorrecibo.dtos.request.recibo.ReciboPrintRequestDTO;
import com.ginc.geradorrecibo.dtos.request.recibo.ReciboRequestDTO;

import com.ginc.geradorrecibo.services.recibo.ReciboService;
import com.ginc.geradorrecibo.utils.Geral;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;


@RestController
@RequestMapping(value = "/recibo")
public class ReciboController {

    @Autowired
    ReciboService reciboService;

    @PostMapping("/registrar")
    public ResponseEntity registrarRecibo(@Valid @RequestBody ReciboRequestDTO reciboRequestDTO, @RequestHeader(name = "token") String token) {
        return reciboService.registrarRecibo(reciboRequestDTO, token);
    }

    @GetMapping("/getRecibosPorUsuario")
    public ResponseEntity getPacientesPorUsuario(@RequestParam Long prk, @RequestHeader(name = "token") String token) {
        return reciboService.getRecibosPorUsuario(prk, token);
    }

    @GetMapping("/getRecibosPorPaciente")
    public ResponseEntity getRecibosPorPaciente(@RequestParam Long prk, @RequestParam Long idus, @RequestHeader(name = "token") String token) {
        return reciboService.getRecibosPorPaciente(prk, token, idus);
    }

    @PostMapping("/gerarRecibo")
    public void exportToPDF(HttpServletResponse response, @Valid @RequestBody ReciboPrintRequestDTO reciboPrintRequestDTO) throws DocumentException, IOException {
       reciboService.imprimir(response, reciboPrintRequestDTO.getPrksRecibo(), reciboPrintRequestDTO.getPrkUsuario());
    }

    @GetMapping("/calcularImpostoAnualPF")
    public ResponseEntity calcularImpostoAnualPF(@RequestParam Long prk, @RequestHeader(name = "token") String token){
        return reciboService.calcularImpostoAnualPF(prk,token);
    }

    @PostMapping("/debugRecibo")
    public void debugRecibo(@Valid @RequestBody ReciboRequestDTO reciboRequestDTO) throws DocumentException, IOException {
        for(LocalDate data : reciboRequestDTO.getDatasProcedimento()){
            System.out.println(data);
        }
    }
}
