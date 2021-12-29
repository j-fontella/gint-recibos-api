package com.ginc.geradorrecibo.repositorys.recibo;

import com.ginc.geradorrecibo.models.recibo.Recibo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReciboRepository extends JpaRepository<Recibo, Long> {

    @Query(value = "SELECT * FROM recibo.recibo AS rec JOIN paciente.paciente as pac ON rec.frk_paciente = pac.prk JOIN servico.servico as serv ON rec.frk_servico = serv.prk where pac.frk_usuario = :prkUsuario", nativeQuery = true)
    Optional<List<Recibo>> getRecibosPorUsuario(Long prkUsuario);

    @Query(value = "SELECT * FROM recibo.recibo AS rec JOIN paciente.paciente as pac ON rec.frk_paciente = pac.prk JOIN servico.servico as serv ON rec.frk_servico = serv.prk where rec.prk IN :prks", nativeQuery = true)
    Optional<List<Recibo>> getRecibosPorPrk(List<Long> prks);

    @Query(value = "SELECT * FROM recibo.recibo AS rec JOIN paciente.paciente as pac ON rec.frk_paciente = pac.prk JOIN servico.servico as serv ON rec.frk_servico = serv.prk where pac.prk = :prkPaciente ORDER BY rec.data DESC", nativeQuery = true)
    Optional<List<Recibo>> getRecibosPorPaciente(Long prkPaciente);

    @Query(value = "SELECT SUM(valor) FROM recibo.recibo AS rec JOIN paciente.paciente as pac ON rec.frk_paciente = pac.prk where pac.frk_usuario = :prkUsuario AND rec.data BETWEEN :anoInicial AND :anoFinal ", nativeQuery = true)
    Optional<Float> getTotalAnualRecibosEmitidos(Long prkUsuario, LocalDate anoInicial, LocalDate anoFinal);






}
