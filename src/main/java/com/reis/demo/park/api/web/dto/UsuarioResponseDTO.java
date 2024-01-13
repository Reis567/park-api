package com.reis.demo.park.api.web.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UsuarioResponseDTO {
    private Long id;
    private String username;
    private String role;
}
