package com.reis.demo.park.api.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.reis.demo.park.api.entity.Vaga;
import com.reis.demo.park.api.service.VagaService;
import com.reis.demo.park.api.web.dto.VagaCreateDTO;
import com.reis.demo.park.api.web.dto.VagaResponseDTO;
import com.reis.demo.park.api.web.dto.mapper.VagaMapper;
import com.reis.demo.park.api.web.exception.ErrorMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Vagas", description = "Recurso para criar e buscar vagas de estacionamento.")
@RestController
@RequestMapping("/api/v1/vagas")
@RequiredArgsConstructor
public class VagaController {
    @Autowired
    private VagaService vagaService;

    @Operation(
    summary = "Criar nova vaga",
    description = "Recurso para criar uma nova vaga de estacionamento.",
    security = @SecurityRequirement(name = "security"),
    responses = {
        @ApiResponse(responseCode = "201", description = "Vaga criada com sucesso.",
            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = VagaResponseDTO.class))),
        @ApiResponse(responseCode = "409", description = "Código de vaga já existe no sistema.",
            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
        @ApiResponse(responseCode = "422", description = "Recurso não processado por falta de dados ou dados inválidos.",
            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
    }
)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VagaResponseDTO> create(@RequestBody @Valid VagaCreateDTO vagaCreateDTO) {
        Vaga vaga = VagaMapper.toVaga(vagaCreateDTO);

            vagaService.criarVaga(vaga);
            return ResponseEntity.status(HttpStatus.CREATED).body(VagaMapper.toDTO(vaga));
        
    }


    @Operation(
    summary = "Buscar vaga por código",
    description = "Recupera informações de uma vaga específica por código.",
    security = @SecurityRequirement(name = "security"),
    responses = {
        @ApiResponse(responseCode = "200", description = "Vaga encontrada com sucesso.",
            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = VagaResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Vaga não encontrada.",
            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
        @ApiResponse(responseCode = "403", description = "Recurso não permitido ao perfil ADMIN",
            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
    }
)
    @GetMapping("/{codigo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VagaResponseDTO> getByCodigo(@PathVariable String codigo) {
        Vaga vaga = vagaService.buscarPorCodigo(codigo);
        return ResponseEntity.ok(VagaMapper.toDTO(vaga));
    }
}