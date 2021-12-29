package com.ginc.geradorrecibo.models.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldNameConstants
@Entity
@Table(name = "TokenReset", schema = "login")
public class TokenReset {

    @Id
    private String hash;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "frk_usuario", referencedColumnName = "prk")
    private Usuario usuario;

    @Column
    private LocalDateTime dataExpiracao;

}
