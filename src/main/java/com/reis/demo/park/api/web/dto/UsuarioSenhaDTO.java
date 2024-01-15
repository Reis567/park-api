package com.reis.demo.park.api.web.dto;

import jakarta.validation.constraints.*;
import lombok.*;


@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UsuarioSenhaDTO {

    @NotBlank
    @Size(min = 6)
    private String senhaAtual;
    
    @NotBlank
    @Size(min = 6)
    private String novaSenha;

    @NotBlank
    @Size(min = 6)
    private String confirmaSenha;
}
    
