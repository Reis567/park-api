package com.reis.demo.park.api.web.dto;

import com.reis.demo.park.api.entity.Vaga;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VagaCreateDTO {

    @NotBlank
    @Size(min = 1, max = 4)
    private String codigo;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Vaga.StatusVaga status;
}