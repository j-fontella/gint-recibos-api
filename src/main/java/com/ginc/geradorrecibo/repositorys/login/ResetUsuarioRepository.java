package com.ginc.geradorrecibo.repositorys.login;


import com.ginc.geradorrecibo.models.login.Token;
import com.ginc.geradorrecibo.models.login.TokenReset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ResetUsuarioRepository extends JpaRepository<TokenReset, String> {

    @Transactional
    @Query(value = "select * from login.token_reset where frk_usuario = :prkUsuario", nativeQuery = true)
    Optional<TokenReset> findByFrkUsuario(Long prkUsuario);

    Optional<TokenReset> findByHash(String hash);

    @Transactional
    @Modifying
    @Query(value = "delete from login.token_reset where hash = :hash", nativeQuery = true)
    void deleteByHash(String hash);

}
