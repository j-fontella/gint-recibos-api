package com.ginc.geradorrecibo.repositorys.endereco;

import com.ginc.geradorrecibo.models.endereco.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    @Transactional
    @Query(value = "select * from endereco.endereco as endereco join paciente.paciente as paciente on paciente.prk = endereco.frk_paciente where endereco.frk_paciente = :frk_paciente", nativeQuery = true)
    Optional<Endereco> getEnderecoPaciente(Long frk_paciente);

}
