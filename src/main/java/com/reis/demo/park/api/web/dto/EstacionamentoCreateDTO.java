package com.reis.demo.park.api.web.dto;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EstacionamentoCreateDTO {

    @NotBlank
    @Pattern(regexp = "^[A-Z]{3}[0-9][A-Z0-9][0-9]{2}$", message = "A placa deve conter apenas letras, números e ter 7 caracteres.")
    private String placa;

    @NotBlank
    private String marca;

    @NotBlank
    private String modelo;

    @NotBlank
    private String cor;

    @NotBlank
    @Size(min = 11, max = 11)
    @CPF
    private String clienteCpf;
}
