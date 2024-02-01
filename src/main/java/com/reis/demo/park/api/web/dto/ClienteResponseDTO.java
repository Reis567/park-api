package com.reis.demo.park.api.web.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class ClienteResponseDTO {
    private Long id;

    private String nome;

    private String cpf;
}
