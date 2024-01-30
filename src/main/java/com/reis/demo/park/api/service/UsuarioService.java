package com.reis.demo.park.api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.reis.demo.park.api.entity.Usuario;
import com.reis.demo.park.api.exception.EntityNotFoundException;
import com.reis.demo.park.api.exception.PasswordConfException;
import com.reis.demo.park.api.exception.UsernameUniqueViolationException;
import com.reis.demo.park.api.repository.UsuarioRepository;


import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Transactional
    public Usuario salvar (Usuario usuario){
        try {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
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
    public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
        log.info("Iniciando a edição de senha para o usuário com ID {}", id);

        if (!novaSenha.equals(confirmaSenha)) {
            log.warn("Nova senha não confere com confirmação de senha");
            throw new PasswordConfException("Nova senha não confere com confirmação de senha");
        }
        

        Usuario user = buscarPorId(id);
        if (!passwordEncoder.matches(senhaAtual, user.getPassword())) {
            log.warn("Senha atual não confere para o usuário com ID {}", id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sua senha não confere");
        }

        user.setPassword(passwordEncoder.encode(novaSenha));
        log.info("Senha do usuário com ID {} atualizada com sucesso", id);

        return user;
    }

    @Transactional(readOnly=true)
    public Usuario buscarPorUsername(String username){
        return usuarioRepository.findByUsername(username).orElseThrow(
            () -> new EntityNotFoundException(String.format("Usuário '%s' não encontrado !"))
        );
    }

    @Transactional(readOnly=true)
    public Usuario.Role buscarRolePorUsuario(String username){
        return usuarioRepository.findRoleByUsername(username);
    }
    

}
