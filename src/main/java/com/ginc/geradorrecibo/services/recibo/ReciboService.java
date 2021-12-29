package com.ginc.geradorrecibo.services.recibo;

import com.ginc.geradorrecibo.dtos.request.recibo.ReciboRequestDTO;
import com.ginc.geradorrecibo.dtos.response.recibo.RecibosAnuaisEmitidosResponseDTO;
import com.ginc.geradorrecibo.exceptions.UserNotFoundException;
import com.ginc.geradorrecibo.models.login.Usuario;
import com.ginc.geradorrecibo.models.recibo.Recibo;
import com.ginc.geradorrecibo.pdf.ReciboPDF;
import com.ginc.geradorrecibo.repositorys.login.UsuarioRepository;
import com.ginc.geradorrecibo.repositorys.recibo.ReciboRepository;
import com.ginc.geradorrecibo.services.security.SecurityService;
import com.ginc.geradorrecibo.utils.Geral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
public class ReciboService {

    @Autowired
    SecurityService securityService;

    @Autowired
    ReciboRepository reciboRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public ResponseEntity<?> registrarRecibo(ReciboRequestDTO reciboRequestDTO, String token){
        try {
            securityService.validarToken(token, reciboRequestDTO.getFrkUsuario());
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
        reciboRepository.save(Geral.converterReciboRequest(reciboRequestDTO));
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> calcularImpostoAnualPF(Long prk, String token){
        try {
            securityService.validarToken(token, prk);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
        LocalDate dataAnoInicial = LocalDate.of(LocalDate.now().getYear(), 1,1);
        LocalDate dataAnoFinal = LocalDate.of(LocalDate.now().getYear(), 12,31);
        Optional<Float> totalAnualEmitido = reciboRepository.getTotalAnualRecibosEmitidos(prk,dataAnoInicial,dataAnoFinal);
        float valorTotalEmitido = 0F;
        if(totalAnualEmitido.isPresent()){
            valorTotalEmitido = totalAnualEmitido.get();
        }

        float valorTotalAnual;

        float segundaFaixa = 22847.77F;
        float deducaoSegundaFaixa = 1713.58F;

        float terceiraFaixa = 33919.81F;
        float deducaoTerceiraFaixa = 4257.57F;

        float quartaFaixa = 45012.61F;
        float deducaoQuartaFaixa =  7633.51F;

        float quintaFaixa = 55976.16F;
        float deducaoQuintaFaixa = 10432.32F;

        if(valorTotalEmitido > quintaFaixa){
            valorTotalAnual = valorTotalEmitido / 100 * 27.5F - deducaoQuintaFaixa;
            return ResponseEntity.ok().body(new RecibosAnuaisEmitidosResponseDTO(valorTotalAnual, "27.5%", "Quinta", null, valorTotalEmitido));
        }
        if(valorTotalEmitido >= quartaFaixa){
            valorTotalAnual = valorTotalEmitido / 100 * 22.5F - deducaoQuartaFaixa;
            return ResponseEntity.ok().body(new RecibosAnuaisEmitidosResponseDTO(valorTotalAnual, "22.5%", "Quarta", 55976.16F, valorTotalEmitido));
        }
        if(valorTotalEmitido >= terceiraFaixa){
            valorTotalAnual = valorTotalEmitido / 100 * 15F - deducaoTerceiraFaixa;
            return ResponseEntity.ok().body(new RecibosAnuaisEmitidosResponseDTO(valorTotalAnual,"15%","Terceira", 45012.60F, valorTotalEmitido));
        }
        if(valorTotalEmitido >= segundaFaixa){
            valorTotalAnual = valorTotalEmitido / 100 * 7.5F - deducaoSegundaFaixa;
            return ResponseEntity.ok().body(new RecibosAnuaisEmitidosResponseDTO(valorTotalAnual, "7.5%", "Segunda",33919.80F, valorTotalEmitido));
        }

        RecibosAnuaisEmitidosResponseDTO responseDTO = new RecibosAnuaisEmitidosResponseDTO(0F, "0%", "Isento",22847.76F, valorTotalEmitido);
        return ResponseEntity.ok().body(responseDTO);
    }

    public void imprimir(HttpServletResponse response, List<Long> prks, Long prk) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        Optional<Usuario> u = usuarioRepository.findByPrk(prk);
        if(u.isEmpty()){
            throw new UserNotFoundException("Usuário não encontrado");
        }
        Optional<List<Recibo>> lr = reciboRepository.getRecibosPorPrk(prks);
        if(lr.isEmpty()){
            throw new UserNotFoundException("Usuário não encontrado");
        }
        Usuario usuario = u.get();
        List<Recibo> listaRecibos = lr.get();
        ReciboPDF pdf = new ReciboPDF(listaRecibos,usuario);
        pdf.export(response);

    }

    public ResponseEntity<?> getRecibosPorUsuario(Long prk, String token){
        try {
            securityService.validarToken(token, prk);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
        Optional<List<Recibo>> recibos = reciboRepository.getRecibosPorUsuario(prk);
        if(recibos.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body("Nenhum paciente cadastrado para este usuário");
        }
        return ResponseEntity.status(HttpStatus.OK).body(Geral.converterReciboResponseToList(recibos.get()));
    }

    public ResponseEntity<?> getRecibosPorPaciente(Long prk, String token, Long frkUsuario){
        try {
            securityService.validarToken(token, frkUsuario);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
        Optional<List<Recibo>> recibos = reciboRepository.getRecibosPorPaciente(prk);
        if(recibos.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body("Nenhum recibo cadastrado para esse paciente");
        }
        return ResponseEntity.status(HttpStatus.OK).body(Geral.converterReciboResponseToList(recibos.get()));
    }
}
