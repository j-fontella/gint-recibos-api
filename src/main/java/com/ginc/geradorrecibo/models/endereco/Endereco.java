package com.ginc.geradorrecibo.models.endereco;

import com.ginc.geradorrecibo.models.paciente.Paciente;
import com.ginc.geradorrecibo.utils.Geral;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Objects;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldNameConstants
@Entity
@Table(name = "Endereco", schema = "endereco")
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prk;

    @Column(columnDefinition = "varchar(50)")
    private String logradouro;

    @Column(columnDefinition = "varchar(20)")
    private String numero;

    @Column(columnDefinition = "varchar(50)")
    private String bairro;

    @Column(columnDefinition = "varchar(50)")
    private String complemento;

    @Column(columnDefinition = "varchar(50)")
    private String cidade;

    @Column(columnDefinition = "varchar(50)")
    private String uf;

    @Column(columnDefinition = "char(8)")
    private String cep;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return Objects.equals(logradouro, endereco.logradouro) && Objects.equals(numero, endereco.numero) && Objects.equals(bairro, endereco.bairro) && Objects.equals(complemento, endereco.complemento) && Objects.equals(cidade, endereco.cidade) && Objects.equals(uf, endereco.uf) && Objects.equals(cep, endereco.cep);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logradouro, numero, bairro, complemento, cidade, uf, cep);
    }

    public String getEnderecoCompleto(){
        return logradouro + ", " + numero + ", " + bairro + ", " + cidade + " CEP " + Geral.formataCep(cep);
    }
}
