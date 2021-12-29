package com.ginc.geradorrecibo.controllers.login;

import com.ginc.geradorrecibo.dtos.request.usuario.*;
import com.ginc.geradorrecibo.services.usuario.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping(value = "/login")
public class LoginController {



    @Autowired
    UsuarioService usuarioService;


    public LoginController() {

    }

    @PostMapping("/registrar")
    public ResponseEntity registrarUsuario(@RequestBody @Valid UsuarioRequestDTO usuarioRequestDTO) {
        return usuarioService.registrarUsuario(usuarioRequestDTO);
    }

    @PostMapping("/atualizarUsuario")
    public ResponseEntity atualizarUsuario(@RequestBody @Valid UsuarioUpdateRequestDTO usuarioUpdateRequestDTO, @RequestHeader(name = "token") String token) {
        return usuarioService.atualizarUsuario(usuarioUpdateRequestDTO, token);
    }

    @PostMapping("/getUser")
    public ResponseEntity getUser(@RequestBody UsuarioDefaultRequestDTO usuarioDefaultRequestDTO, @RequestHeader(name = "token") String token) {
        return usuarioService.getUsuarioPorPrk(usuarioDefaultRequestDTO.getPrk(), token);
    }

    @PostMapping("/esqueciSenha")
    public ResponseEntity esqueciSenha(@RequestBody @Valid UsuarioResetPasswordRequestDTO usuarioResetPasswordRequestDTO) throws NoSuchAlgorithmException {
        return usuarioService.esqueciSenha(usuarioResetPasswordRequestDTO.getEmail());
    }

    @PostMapping("/recuperar")
    public ResponseEntity recuperar(@RequestBody @Valid UsuarioRecuperarPasswordRequestDTO usuarioRecuperarPasswordRequestDTO) {
        return usuarioService.recuperarSenha(usuarioRecuperarPasswordRequestDTO);
    }

    @PostMapping("/on")
    public ResponseEntity loginUsuario(@RequestBody @Valid UsuarioLoginRequestDTO usuarioLoginRequestDTO) throws NoSuchAlgorithmException {
        return usuarioService.logarUsuario(usuarioLoginRequestDTO);
    }

}
