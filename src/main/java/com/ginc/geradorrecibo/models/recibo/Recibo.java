package com.ginc.geradorrecibo.models.recibo;

import com.ginc.geradorrecibo.domains.Procedimento;
import com.ginc.geradorrecibo.models.endereco.Endereco;
import com.ginc.geradorrecibo.models.paciente.Paciente;
import com.ginc.geradorrecibo.models.servico.Servico;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode()
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldNameConstants
@Entity
@Table(name = "Recibo", schema = "recibo")
public class Recibo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prk;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "frk_paciente", referencedColumnName = "prk")
    private Paciente paciente;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "frk_servico", referencedColumnName = "prk")
    private Servico servico;

    @Column(columnDefinition = "DATE")
    private LocalDate data;

    @Column(columnDefinition = "varchar(50)")
    private String cidade;

    @Column
    private Double valor;

    @Enumerated(EnumType.STRING)
    private Procedimento tipoProcedimento;

    @Column
    private Integer qtdTipoProcedimento;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "frk_recibo", referencedColumnName = "prk")
    private List<DataProcedimento> datasProcedimento;

    @Column
    private String observacao;



}
