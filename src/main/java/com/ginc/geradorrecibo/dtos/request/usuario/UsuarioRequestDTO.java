package com.ginc.geradorrecibo.dtos.request.usuario;

import com.ginc.geradorrecibo.models.endereco.Endereco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode()
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldNameConstants
public class UsuarioRequestDTO {

    private Long prk;

    @NotBlank(message = "Nome deve ser preenchido")
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres.")
    private String nome;

    @NotBlank(message = "Email deve ser preenchido")
    @Email(message = "Você deve inserir um email válido.")
    private String email;

    @NotBlank(message = "Senha deve ser preenchida")
    @Size(min = 6, max = 20, message = "A senha deve ter entre 6 e 20 caracteres.")
    private String senha;

    @NotNull(message = "Endereço inválido")
    private Endereco endereco;

    @NotBlank(message = "CPF/CNPJ deve ser preenchido")
    @Size(min = 11, max = 14, message = "CPF/CNPJ inválido.")
    private String docregistro;

    @NotBlank(message = "Conselho deve ser preenchido")
    @Size(min = 3, max = 100, message = "O conselho deve ter entre 3 e 50 caracteres.")
    private String conselho;

    @NotBlank(message = "Numero do conselho deve ser preenchido")
    @Size(min = 3, max = 50, message = "O numero do conselho deve ter entre 3 e 50 caracteres.")
    private String numeroConselho;

    @NotBlank(message = "Profissão deve ser preenchida")
    @Size(min = 3, max = 50, message = "A profissão deve ter entre 3 e 50 caracteres.")
    private String profissao;

}
