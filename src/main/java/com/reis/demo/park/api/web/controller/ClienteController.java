package com.reis.demo.park.api.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reis.demo.park.api.config.jwt.JwtUserDetails;
import com.reis.demo.park.api.entity.Cliente;
import com.reis.demo.park.api.service.ClienteService;
import com.reis.demo.park.api.service.UsuarioService;
import com.reis.demo.park.api.web.dto.ClienteCreateDTO;
import com.reis.demo.park.api.web.dto.ClienteResponseDTO;
import com.reis.demo.park.api.web.dto.mapper.ClienteMapper;
import com.reis.demo.park.api.web.exception.ErrorMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@Tag(name="Clientes", description = "Recurso para criar um novo cliente vinculado a um usuário cadastrado . ")
@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

    @Autowired
    private  ClienteService clienteService;
    @Autowired
    private UsuarioService usuarioService;
    

    @Operation(summary = "Criar novo cliente", description = "Recurso para criar um novo cliente vinculado a um usuário cadastrado.",
    responses = {
        @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso ",
        content = @Content(mediaType = "application/json;charset=UTF-8",schema = @Schema(implementation = ClienteResponseDTO.class))),
        @ApiResponse(responseCode = "409", description = "Cliente já possui CPF cadastrado no sistema",
        content = @Content(mediaType = "application/json;charset=UTF-8",schema = @Schema(implementation = ErrorMessage.class))),
        @ApiResponse(responseCode = "422", description = "Recurso não processado por falta de dados ou dados inválidos",
        content = @Content(mediaType = "application/json;charset=UTF-8",schema = @Schema(implementation = ErrorMessage.class))),
        @ApiResponse(responseCode = "403", description = "Recurso não permitido ao perfil ADMIN",
        content = @Content(mediaType = "application/json;charset=UTF-8",schema = @Schema(implementation = ErrorMessage.class))),
    } )
    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<ClienteResponseDTO> create(@RequestBody @Valid ClienteCreateDTO clienteCreateDTO, @AuthenticationPrincipal JwtUserDetails userDetails){
        System.out.println("Nome: " + clienteCreateDTO.getNome());
        System.out.println("CPF: " + clienteCreateDTO.getCpf());
        Cliente cliente = ClienteMapper.toCliente(clienteCreateDTO);
        cliente.setUsuario(usuarioService.buscarPorId(userDetails.getId()));

        clienteService.salvar(cliente);

        return ResponseEntity.status(HttpStatus.CREATED).body(ClienteMapper.toDTO(cliente));
    }
}
