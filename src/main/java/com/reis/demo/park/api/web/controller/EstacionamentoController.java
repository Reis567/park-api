package com.reis.demo.park.api.web.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.reis.demo.park.api.entity.ClienteVaga;
import com.reis.demo.park.api.service.ClienteVagaService;
import com.reis.demo.park.api.service.EstacionamentoService;
import com.reis.demo.park.api.web.dto.EstacionamentoCreateDTO;
import com.reis.demo.park.api.web.dto.EstacionamentoResponseDTO;
import com.reis.demo.park.api.web.dto.mapper.ClienteVagaMapper;
import com.reis.demo.park.api.web.exception.ErrorMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name="Estacionamentos", description = "Contém todas as operações relativas ao estacionamento ")
@RestController
@RequestMapping("api/v1/estacionamentos")
@RequiredArgsConstructor
public class EstacionamentoController {


    private final EstacionamentoService estacionamentoService;

    private final ClienteVagaService clienteVagaService;


    @Operation(
    summary = "Realizar check-in no estacionamento",
    security = @SecurityRequirement(name = "security"),
    description = "Realiza o check-in de um cliente no estacionamento.",
    responses = {
         @ApiResponse(responseCode = "201", description = "Check-in realizado com sucesso.",
            headers = @Header(name = "Location", description = "URI do recurso criado.", schema = @Schema(type = "string")),
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EstacionamentoResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Nenhuma vaga disponível , ou CPF usado não cadastrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
        @ApiResponse(responseCode = "422", description = "Cliente não encontrado ou problema nos dados enviados ou dados inválidos.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
        @ApiResponse(responseCode = "403", description = "Operação não permitida para o perfil cliente.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
    }
)
    @PostMapping("/check-in")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EstacionamentoResponseDTO> checkin(@RequestBody @Valid EstacionamentoCreateDTO estacionamentoCreateDTO){
            ClienteVaga clienteVaga = ClienteVagaMapper.toClienteVaga(estacionamentoCreateDTO);

            ClienteVaga clienteVagaCheckIn = estacionamentoService.checkIn(clienteVaga);

            EstacionamentoResponseDTO responseDTO = ClienteVagaMapper.toDTO(clienteVagaCheckIn);

            URI location = ServletUriComponentsBuilder
                            .fromCurrentRequestUri().path("/{recibo}")
                            .buildAndExpand(clienteVaga.getRecibo())
                            .toUri();

            return ResponseEntity.status(HttpStatus.CREATED).location(location).body(responseDTO);
    }
    


    @Operation(
    summary = "Buscar check-in por recibo",
    security = @SecurityRequirement(name = "security"),
    description = "Retorna os detalhes do check-in associado ao recibo fornecido.",
    responses = {
        @ApiResponse(responseCode = "200", description = "Check-in encontrado com sucesso.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EstacionamentoResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Check-in não encontrado.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    }
)
    @GetMapping("/check-in/{recibo}")
    @PreAuthorize("hasAnyRole('ADMIN','CLIENTE')")
    public ResponseEntity<EstacionamentoResponseDTO> getByRecibo(@PathVariable String recibo){
        ClienteVaga clienteVaga = clienteVagaService.buscarPorRecibo(recibo);

        EstacionamentoResponseDTO estacionamentoResponseDTO = ClienteVagaMapper.toDTO(clienteVaga);
        return ResponseEntity.ok(estacionamentoResponseDTO);
    }


    @PutMapping("/check-out/{recibo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EstacionamentoResponseDTO> checkout(@PathVariable String recibo){
        ClienteVaga clienteVaga = estacionamentoService.checkOut(recibo);

        EstacionamentoResponseDTO estacionamentoResponseDTO = ClienteVagaMapper.toDTO(clienteVaga);
        return ResponseEntity.ok(estacionamentoResponseDTO);
    }
}
