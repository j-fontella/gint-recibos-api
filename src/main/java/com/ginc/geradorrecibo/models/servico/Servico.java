package com.ginc.geradorrecibo.models.servico;

import com.ginc.geradorrecibo.models.login.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@EqualsAndHashCode()
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldNameConstants
@Entity
@Table(name = "Servico", schema = "servico")
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prk;

    @Column(columnDefinition = "varchar(100)")
    private String nome;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "frk_usuario", referencedColumnName = "prk")
    private Usuario usuario;
}
