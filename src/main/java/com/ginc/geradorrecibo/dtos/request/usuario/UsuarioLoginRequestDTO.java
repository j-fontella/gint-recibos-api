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
public class UsuarioLoginRequestDTO {

    @NotBlank
    @Email(message = "Você deve inserir um email válido.")
    private String email;

    @NotBlank
    @Size(min = 6, max = 20, message = "A senha deve ter entre 6 e 20 caracteres.")
    private String senha;
}
