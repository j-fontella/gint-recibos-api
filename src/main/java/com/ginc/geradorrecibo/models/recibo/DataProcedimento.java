package com.ginc.geradorrecibo.models.recibo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;

@EqualsAndHashCode()
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldNameConstants
@Entity
@Table(name = "DataProcedimento", schema = "recibo")
public class DataProcedimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prk;

    @Column(columnDefinition = "DATE")
    private LocalDate data;
}
