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
import jakarta.validation.Valid;


@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

    @Autowired
    private  ClienteService clienteService;
    @Autowired
    private UsuarioService usuarioService;
    
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
