package com.ginc.geradorrecibo.dtos.request.recibo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ginc.geradorrecibo.domains.Procedimento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import org.apache.tomcat.jni.Local;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode()
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldNameConstants
public class ReciboRequestDTO {
    @NotNull(message = "Erro, contate o suporte.")
    private Long frkUsuario;

    @NotNull(message = "Paciente inválido.")
    private Long frkPaciente;

    @NotNull(message = "Serviço inválido.")
    private Long frkServico;

    @NotNull(message = "O valor é obrigatório")
    private Double valor;

    @NotNull(message = "A data é obrigatória")
    @JsonFormat(pattern = "yyyy-MM-dd", shape=JsonFormat.Shape.STRING)
    private LocalDate data;

    @JsonFormat(pattern = "yyyy-MM-dd", shape=JsonFormat.Shape.STRING)
    private List<LocalDate> datasProcedimento;

    @NotBlank(message = "Cidade deve ser preenchida")
    @Size(min = 3, max = 50, message = "A cidade deve ter entre 3 e 50 caracteres.")
    private String cidade;

    private String observacao;

    private Procedimento tipoProcedimento;

    private Integer qtdTipoProcedimento;


}
