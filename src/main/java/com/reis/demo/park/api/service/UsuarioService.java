package com.reis.demo.park.api.service;

import java.util.List;



import org.springframework.stereotype.Service;

import com.reis.demo.park.api.entity.Usuario;
import com.reis.demo.park.api.exception.EntityNotFoundException;
import com.reis.demo.park.api.exception.UsernameUniqueViolationException;
import com.reis.demo.park.api.repository.UsuarioRepository;


import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    
    @Transactional
    public Usuario salvar (Usuario usuario){
        try {
            return usuarioRepository.save(usuario);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new UsernameUniqueViolationException(String.format("Username {%s} já cadastrado", usuario.getUsername()));
        }
    }

    @Transactional(readOnly=true)
    public Usuario buscarPorId(Long id){
        return usuarioRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException(String.format("Usuário não encontrado !",id))
        );

    }

    @Transactional(readOnly=true)
    public List<Usuario> buscarTodos(){
        return usuarioRepository.findAll();

    }

    @Transactional
    public Usuario editarSenha(Long id , String senhaAtual , String novaSenha, String confirmaSenha){
        if(!novaSenha.equals(confirmaSenha)){
            throw new RuntimeException("Nova senha não confere com confirmação de senha ");
        }

        Usuario user = buscarPorId(id);
        if (!user.getPassword().equals(senhaAtual)) {
            throw new RuntimeException("Sua senha não confere");
        }
        user.setPassword(novaSenha);
        return user;
    }

}
