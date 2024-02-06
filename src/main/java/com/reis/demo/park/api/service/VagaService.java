package com.reis.demo.park.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reis.demo.park.api.entity.Vaga;
import com.reis.demo.park.api.repository.VagaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class VagaService {
    private final VagaRepository vagaRepository;

    @Transactional
    public Vaga criarVaga(Vaga vaga) throws Exception {
        try {
            log.info("Criando vaga: {}", vaga);
            if (vagaRepository.existsByCodigo(vaga.getCodigo())) {
                throw new Exception(String.format("Código de vaga '%s' já existe", vaga.getCodigo()));
            }
            Vaga createdVaga = vagaRepository.save(vaga);
            log.info("Vaga criada com sucesso: {}", createdVaga);
            return createdVaga;
        } catch (Exception ex) {
            log.error("Erro ao criar vaga. Motivo: {}", ex.getMessage());
            throw new Exception(String.format("Erro ao criar vaga. Motivo: '%s'", ex.getMessage()));
        }
    }
    
}
