package com.reis.demo.park.api.web.dto;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class ClienteCreateDTO {
    @NotBlank
    @Size(min = 3 ,max = 130)
    private String nome;

    @NotBlank
    @Size(min = 11 ,max = 11)
    @CPF
    private String cpf;
}
