package com.ginc.geradorrecibo.repositorys.login;

import com.ginc.geradorrecibo.models.login.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByPrk(Long prk);


}
