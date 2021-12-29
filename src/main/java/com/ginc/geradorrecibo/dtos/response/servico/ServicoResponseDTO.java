package com.ginc.geradorrecibo.dtos.response.servico;

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
public class ServicoResponseDTO {
    private String nome;
    private Long prk;
}
