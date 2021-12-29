package com.ginc.geradorrecibo.domains;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

@Getter
@JsonFormat
public enum Procedimento {
    SESSAO(1, "sessão", "sessões"),
    ATENDIMENTO(2, "atendimento", "atendimentos"),
    PROCEDIMENTO(3, "procedimento", "procedimentos"),
    CONSULTA(4, "consulta", "consultas"),
    ;


    private final Integer id;
    private final String singular;
    @JsonValue
    private final String plural;

    Procedimento(Integer id, String singular, String plural) {
        this.id = id;
        this.singular = singular;
        this.plural = plural;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Procedimento get(int id) {
        return Arrays.stream(values()).filter(t -> t.getId() == id).findFirst().orElse(null);
    }
}
