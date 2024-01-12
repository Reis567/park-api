package com.reis.demo.park.api.service;

import java.util.List;

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

    @Transactional(readOnly=true)
    public List<Usuario> buscarTodos(){
        return usuarioRepository.findAll();

    }

    @Transactional
    public Usuario editarSenha(Long id , String password){
        Usuario user = buscarPorId(id);
        user.setPassword(password);
        return user;
    }

}
