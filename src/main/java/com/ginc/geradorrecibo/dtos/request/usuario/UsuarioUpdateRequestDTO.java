package com.ginc.geradorrecibo.dtos.request.usuario;

import com.ginc.geradorrecibo.models.endereco.Endereco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

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
public class UsuarioUpdateRequestDTO {

    @NotNull(message = "Contate o suporte")
    private Long prk;

    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres.")
    private String nome;

    @Email(message = "Você deve inserir um email válido.")
    private String email;

    @NotBlank(message = "Senha deve ser preenchida")
    @Size(min = 6, max = 20, message = "A senha deve ter entre 6 e 20 caracteres.")
    private String senha;

    @Size(min = 6, max = 20, message = "A senha deve ter entre 6 e 20 caracteres.")
    private String novaSenha;

    private Endereco endereco;

    @Size(min = 11, max = 14, message = "CPF/CNPJ inválido.")
    private String docregistro;

    @Size(min = 3, max = 100, message = "O conselho deve ter entre 3 e 50 caracteres.")
    private String conselho;

    @Size(min = 3, max = 50, message = "O numero do conselho deve ter entre 3 e 50 caracteres.")
    private String numeroConselho;


    @Size(min = 3, max = 50, message = "A profissão deve ter entre 3 e 50 caracteres.")
    private String profissao;
}
