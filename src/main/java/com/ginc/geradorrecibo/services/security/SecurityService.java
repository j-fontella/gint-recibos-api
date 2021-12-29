package com.ginc.geradorrecibo.services.security;

import com.ginc.geradorrecibo.models.login.Token;
import com.ginc.geradorrecibo.repositorys.login.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SecurityService {

    @Autowired
    private TokenRepository tokenRepository;

    public void validarToken(String hash, Long prk){
        Optional<Token> tokenQuery = tokenRepository.findByFrkUsuario(prk);
        if(tokenQuery.isEmpty()){
            throw new SecurityException("Token não cadastrado.");
        }
        Token token = tokenQuery.get();
        if(!token.getHash().equals(hash)){
            throw new SecurityException("Token inválido.");
        }
        if(LocalDateTime.now().isAfter(token.getDataExpiracao())){
            throw new SecurityException("Token expirado.");
        }

    }

}
