package com.ginc.geradorrecibo.dtos.request.endereco;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode()
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldNameConstants
public class EnderecoRequestDTO {

    @NotBlank(message = "Logradouro inválido.")
    @Size(min = 3, max = 50, message = "O logradouro deve ter entre 3 e 50 caracteres.")
    private String logradouro;

    @NotBlank(message = "Numero inválido.")
    @Size(min = 3, max = 50, message = "O numero deve ter entre 3 e 20 caracteres.")
    private String numero;

    @NotBlank(message = "Bairro inválido.")
    @Size(min = 3, max = 50, message = "O bairro deve ter entre 3 e 50 caracteres.")
    private String bairro;

    @Size(min = 3, max = 50, message = "O complemento deve ter entre 3 e 50 caracteres.")
    private String complemento;

    @NotBlank(message = "Cidade inválida.")
    @Size(min = 3, max = 50, message = "A cidade deve ter entre 3 e 50 caracteres.")
    private String cidade;

    @NotBlank(message = "UF inválida.")
    @Size(min = 2, max = 2, message = "UF deve ter 2 caracteres.")
    private String uf;

    @NotBlank(message = "CEP inválido.")
    @Size(min = 8, max = 8, message = "O CEP deve ter 8 caracteres.")
    private String cep;

    @NotNull(message = "Contate o suporte.")
    private Long frkUsuario;


}
