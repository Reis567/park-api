package com.reis.demo.park.api.web.dto;


import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UsuarioCreateDTO {

    private String username;
    private String password;
}
