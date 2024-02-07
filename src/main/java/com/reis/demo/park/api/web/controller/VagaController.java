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

import com.reis.demo.park.api.config.jwt.JwtUserDetails;
import com.reis.demo.park.api.entity.Vaga;
import com.reis.demo.park.api.service.VagaService;
import com.reis.demo.park.api.web.dto.VagaCreateDTO;
import com.reis.demo.park.api.web.dto.VagaResponseDTO;
import com.reis.demo.park.api.web.dto.mapper.VagaMapper;

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

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VagaResponseDTO> create(@RequestBody @Valid VagaCreateDTO vagaCreateDTO) throws Exception {
        Vaga vaga = VagaMapper.toVaga(vagaCreateDTO);

            vagaService.criarVaga(vaga);
            return ResponseEntity.status(HttpStatus.CREATED).body(VagaMapper.toDTO(vaga));
        
    }

    @GetMapping("/{codigo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VagaResponseDTO> getByCodigo(@PathVariable String codigo) {
        Vaga vaga = vagaService.buscarPorCodigo(codigo);
        return ResponseEntity.ok(VagaMapper.toDTO(vaga));
    }
}