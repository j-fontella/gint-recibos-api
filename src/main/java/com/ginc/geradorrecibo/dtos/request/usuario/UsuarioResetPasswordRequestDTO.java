package com.ginc.geradorrecibo.dtos.request.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@EqualsAndHashCode()
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldNameConstants
public class UsuarioResetPasswordRequestDTO {
    @NotBlank(message = "Email deve ser preenchido")
    @Email(message = "Você deve inserir um email válido.")
    private String email;
}
