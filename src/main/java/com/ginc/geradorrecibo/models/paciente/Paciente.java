package com.ginc.geradorrecibo.models.paciente;

import com.ginc.geradorrecibo.models.endereco.Endereco;
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
@Table(name = "Paciente", schema = "paciente")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prk;

    @Column(columnDefinition = "varchar(100)")
    private String nome;

    @Column(columnDefinition = "char(11)")
    private String cpf;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "frk_usuario", referencedColumnName = "prk")
    private Usuario usuario;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "frk_endereco", referencedColumnName = "prk")
    private Endereco endereco;


}
