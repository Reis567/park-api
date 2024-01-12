package com.reis.demo.park.api.service;

import org.springframework.stereotype.Service;

import com.reis.demo.park.api.entity.Usuario;
import com.reis.demo.park.api.repository.UsuarioRepository;


import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    
    @Transactional
    public Usuario salvar (Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly=true)
    public Usuario buscarPorId(Long id){
        return usuarioRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Usuário não encontrado !")
        );
    }

}
