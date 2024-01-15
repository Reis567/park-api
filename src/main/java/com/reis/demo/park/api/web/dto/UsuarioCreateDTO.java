package com.reis.demo.park.api.web.dto;


import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UsuarioCreateDTO {

    @Email(message = "Formato de emai. Inválido")
    @NotBlank
    private String username;
    private String password;
}
