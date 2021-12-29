package com.ginc.geradorrecibo.dtos.request.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode()
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldNameConstants
public class TokenRequestDTO {

   @NotBlank(message = "A hash é obrigatória")
   private String hash;
}
