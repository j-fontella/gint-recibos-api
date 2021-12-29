package com.ginc.geradorrecibo.repositorys.servico;


import com.ginc.geradorrecibo.models.login.Usuario;
import com.ginc.geradorrecibo.models.paciente.Paciente;
import com.ginc.geradorrecibo.models.servico.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ServicoRepository extends JpaRepository<Servico, Long> {

    @Transactional
    @Query(value = "select * from servico.servico where frk_usuario = :frk_usuario and nome = :nome", nativeQuery = true)
    Optional<Servico> findByNome(String nome, Long frk_usuario);

    @Transactional
    @Query(value = "select * from servico.servico where frk_usuario = :frk_usuario", nativeQuery = true)
    Optional<List<Servico>> getServicosPorUsuario(Long frk_usuario);

    Optional<Servico> findByPrk(Long prk);


}
