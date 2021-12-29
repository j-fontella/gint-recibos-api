package com.ginc.geradorrecibo.dtos.response.login;

import com.ginc.geradorrecibo.models.endereco.Endereco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode()
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldNameConstants
public class UsuarioResponseDTO {
    private String nome;
    private String email;
    private Endereco endereco;
    private String docregistro;
    private String conselho;
    private String numeroConselho;
    private String profissao;
}
