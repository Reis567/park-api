package com.reis.demo.park.api.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reis.demo.park.api.entity.ClienteVaga;
import com.reis.demo.park.api.service.EstacionamentoService;
import com.reis.demo.park.api.web.dto.EstacionamentoCreateDTO;
import com.reis.demo.park.api.web.dto.EstacionamentoResponseDTO;
import com.reis.demo.park.api.web.dto.mapper.ClienteVagaMapper;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name="Estacionamentos", description = "")
@RestController
@RequestMapping("api/v1/estacionamentos")
@RequiredArgsConstructor
public class EstacionamentoController {
    private final EstacionamentoService estacionamentoService;

    @PostMapping("/check-in")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EstacionamentoResponseDTO> checkin(@RequestBody @Valid EstacionamentoCreateDTO estacionamentoCreateDTO){
            ClienteVaga clienteVaga = ClienteVagaMapper.toClienteVaga(estacionamentoCreateDTO);

            ClienteVaga clienteVagaCheckIn = estacionamentoService.checkIn(clienteVaga);
    }
}
