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
import com.reis.demo.park.api.web.exception.ErrorMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name="Usuários", description = "Contém todas as operações relativas ao cadastro edição e leitura de usuários . ")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;


    @Operation(summary = "Criar um novo usuário",
                description = "Recurso para criar um novo usuário",
                responses = {
                    @ApiResponse(responseCode = "201",description = "Recurso criado com sucesso "
                    , content = @Content(mediaType = "application/json",schema = @Schema(implementation = UsuarioResponseDTO.class))),

                    @ApiResponse(responseCode = "409", description = "Usuário email já cadastrado no sistema",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada inválidos",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),
                    
                }
        )
    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponseDTO> create (@Valid @RequestBody UsuarioCreateDTO usuarioCreateDTO){
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(usuarioCreateDTO));
        
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDTO(user));
    }  


    @Operation(summary = "Recuperar um usuário pelo id ",
                description = "Recuperar um usuário pelo id ",
                responses = {
                    @ApiResponse(responseCode = "200",description = "Recurso recuperado com sucesso  "
                    , content = @Content(mediaType = "application/json",schema = @Schema(implementation = UsuarioResponseDTO.class))),

                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),
                    
                }
        )
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> GetById(@PathVariable Long id){
        Usuario user = usuarioService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(UsuarioMapper.toDTO(user));
    }

    @Operation(summary = "Recuperar todos os usuários",
    description = "Recuperar todos os usuários",
    responses = {
        @ApiResponse(responseCode = "200",description = "Usuários recuperados com sucesso"
        , content = @Content(mediaType = "application/json",schema = @Schema(implementation=UsuarioResponseDTO.class)))
        }
    )
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> GetAll(){
        List<Usuario> users = usuarioService.buscarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(UsuarioMapper.toListDTO(users));
    }

    @Operation(summary = "Atualizar senha",
    description = "Atualizar senha",
    responses = {
        @ApiResponse(responseCode = "204",description = "Senha atualizada com sucesso "
        , content = @Content(mediaType = "application/json",schema = @Schema(implementation =Void.class))),

        @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
        content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),
        
        @ApiResponse(responseCode = "400", description = "Senha não confere",
        content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),
        }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> updatePassword( @PathVariable Long id , @Valid @RequestBody UsuarioSenhaDTO usuarioSenhaDTO){
        Usuario user = usuarioService.editarSenha(id, usuarioSenhaDTO.getSenhaAtual(), usuarioSenhaDTO.getNovaSenha(), usuarioSenhaDTO.getConfirmaSenha());
        return ResponseEntity.status(HttpStatus.OK).body(UsuarioMapper.toDTO(user));
    }
    
}
