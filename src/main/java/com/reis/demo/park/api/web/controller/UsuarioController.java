package com.reis.demo.park.api.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reis.demo.park.api.entity.Usuario;
import com.reis.demo.park.api.service.UsuarioService;
import com.reis.demo.park.api.web.dto.UsuarioCreateDTO;
import com.reis.demo.park.api.web.dto.UsuarioResponseDTO;
import com.reis.demo.park.api.web.dto.UsuarioSenhaDTO;
import com.reis.demo.park.api.web.dto.mapper.UsuarioMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;


    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponseDTO> create (@Valid @RequestBody UsuarioCreateDTO usuarioCreateDTO){
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(usuarioCreateDTO));
        
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDTO(user));
    }  

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> GetById(@PathVariable Long id){
        Usuario user = usuarioService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(UsuarioMapper.toDTO(user));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> GetAll(){
        List<Usuario> users = usuarioService.buscarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(UsuarioMapper.toListDTO(users));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> updatePassword(@PathVariable Long id , @RequestBody UsuarioSenhaDTO usuarioSenhaDTO){
        Usuario user = usuarioService.editarSenha(id, usuarioSenhaDTO.getSenhaAtual(), usuarioSenhaDTO.getNovaSenha(), usuarioSenhaDTO.getConfirmaSenha());
        return ResponseEntity.status(HttpStatus.OK).body(UsuarioMapper.toDTO(user));
    }
    
}
