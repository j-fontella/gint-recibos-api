package com.ginc.geradorrecibo.dtos.request.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@EqualsAndHashCode()
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldNameConstants
public class UsuarioRecuperarPasswordRequestDTO {

    @NotBlank(message = "Email inválido")
    @Email(message = "Você deve inserir um email válido.")
    private String email;

    @NotBlank(message = "Token inválido")
    private String hash;

    @NotBlank(message = "Senha inválida")
    private String senha;
}
