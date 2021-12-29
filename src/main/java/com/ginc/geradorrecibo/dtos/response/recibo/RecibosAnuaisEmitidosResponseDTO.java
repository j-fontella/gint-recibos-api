package com.ginc.geradorrecibo.dtos.response.recibo;

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
public class RecibosAnuaisEmitidosResponseDTO {
    private Float totalAnual;
    private String aliquota;
    private String faixa;
    private Float limiteFaixa;
    private Float valorEmitido;
}
