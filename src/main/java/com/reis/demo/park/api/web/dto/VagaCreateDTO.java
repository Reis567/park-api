package com.reis.demo.park.api.web.dto;

import com.reis.demo.park.api.entity.Vaga;

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
    @Pattern(regexp = "LIVRE|OCUPADA")
    private Vaga.StatusVaga status;
}