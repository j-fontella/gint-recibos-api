package com.ginc.geradorrecibo.dtos.request.recibo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@EqualsAndHashCode()
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldNameConstants
public class ReciboPrintRequestDTO {


    @NotNull(message = "Recibos em branco")
    private List<Long> prksRecibo;


    @NotNull(message = "Usuário inválido")
    private Long prkUsuario;

}
