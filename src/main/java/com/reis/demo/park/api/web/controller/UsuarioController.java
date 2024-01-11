package com.reis.demo.park.api.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reis.demo.park.api.entity.Usuario;
import com.reis.demo.park.api.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public ResponseEntity<Usuario> create (Usuario usuario){
        
    }  
    
}
