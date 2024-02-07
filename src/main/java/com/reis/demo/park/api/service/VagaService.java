package com.reis.demo.park.api.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reis.demo.park.api.entity.Vaga;
import com.reis.demo.park.api.exception.CodigoUniqueViolationException;
import com.reis.demo.park.api.exception.EntityNotFoundException;
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
            return vagaRepository.save(vaga);
        } catch (DataIntegrityViolationException ex) {
            log.error("Erro ao criar vaga. Motivo: {}", ex.getMessage());
            throw new CodigoUniqueViolationException(String.format("Erro ao criar vaga. Motivo: '%s'", ex.getMessage()));
        }
    }

    @Transactional(readOnly = true)
    public Vaga buscarPorCodigo(String codigo) {
        log.info("Buscando vaga por código: {}", codigo);
        return vagaRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Vaga não encontrada com o código: " + codigo));
    }
    
}
