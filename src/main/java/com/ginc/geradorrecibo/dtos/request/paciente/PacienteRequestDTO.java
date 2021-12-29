package com.ginc.geradorrecibo.dtos.request.paciente;

import com.ginc.geradorrecibo.dtos.request.endereco.EnderecoRequestDTO;
import com.ginc.geradorrecibo.models.endereco.Endereco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode()
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldNameConstants
public class PacienteRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres.")
    private String nome;

    @NotBlank(message = "O cpf é obrigatório")
    @Size(min = 11, max = 11, message = "O cpf deve ter 11 caracteres.")
    private String cpf;

    @NotNull(message = "Endereço inválido.")
    private EnderecoRequestDTO endereco;

    @NotNull(message = "Erro, contate o suporte.")
    private Long frkUsuario;
}
