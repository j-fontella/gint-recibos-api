package com.ginc.geradorrecibo.models.login;

import com.ginc.geradorrecibo.models.endereco.Endereco;
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
@Table(name = "Usuario", schema = "login")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prk;

    @Column(columnDefinition = "varchar(100)")
    private String nome;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "frk_endereco", referencedColumnName = "prk")
    private Endereco endereco;

    @Column(columnDefinition = "varchar(14)")
    private String docregistro;

    @Column(columnDefinition = "varchar(50)")
    private String conselho;

    @Column(columnDefinition = "varchar(50)")
    private String numeroConselho;

    @Column(columnDefinition = "varchar(50)")
    private String profissao;

    @Column(columnDefinition = "varchar(150)")
    private String email;

    @Column
    private String senha;

}
