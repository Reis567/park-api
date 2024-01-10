package com.reis.demo.park.api.service;

import org.springframework.stereotype.Service;

import com.reis.demo.park.api.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    

}
