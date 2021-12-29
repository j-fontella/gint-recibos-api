package com.ginc.geradorrecibo.dtos.response.recibo;

import com.ginc.geradorrecibo.domains.Procedimento;
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
public class ReciboResponseDTO {
    private Long prk;
    private String paciente;
    private String servico;
    private Double valor;
    private String data;
    private Procedimento tipoProcedimento;
    private Integer qtdTipoProcedimento;
}
