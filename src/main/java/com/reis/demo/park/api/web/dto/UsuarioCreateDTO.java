package com.reis.demo.park.api.web.dto;


import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UsuarioCreateDTO {

    @Email(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",message = "Formato de email Inválido")
    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 6)
    private String password;
}
