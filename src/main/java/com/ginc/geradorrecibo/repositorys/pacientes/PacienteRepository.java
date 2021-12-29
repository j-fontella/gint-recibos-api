package com.ginc.geradorrecibo.repositorys.pacientes;

import com.ginc.geradorrecibo.models.paciente.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    @Transactional
    @Query(value = "SELECT * FROM paciente.paciente AS pac JOIN endereco.endereco as ende ON ende.prk = pac.frk_endereco where frk_usuario = :frk_usuario and cpf = :cpf", nativeQuery = true)
    Optional<Paciente> findByCpfAndPrk(String cpf, Long frk_usuario);

    @Transactional
    @Query(value = "SELECT * FROM paciente.paciente AS pac JOIN endereco.endereco as ende ON ende.prk = pac.frk_endereco where frk_usuario = :frk_usuario", nativeQuery = true)
    Optional<List<Paciente>> findAllByPrk(Long frk_usuario);

}
