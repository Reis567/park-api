package com.reis.demo.park.api.service;

import org.springframework.stereotype.Service;

import com.reis.demo.park.api.entity.ClienteVaga;
import com.reis.demo.park.api.repository.ClienteVagaRepository;
import com.reis.demo.park.api.utils.EstacionamentoUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteVagaService {
    private final ClienteVagaRepository clienteVagaRepository;

    @Transactional
    public ClienteVaga salvar(ClienteVaga clienteVaga) {
        try {
            //clienteVaga.setRecibo(EstacionamentoUtils.gerarRecibo());

            log.info("Salvando clienteVaga: {}", clienteVaga);
            return clienteVagaRepository.save(clienteVaga);
        } catch (Exception ex) {
            log.error("Erro ao salvar clienteVaga. Motivo: {}", ex.getMessage());
            throw ex;
        }
    }
}
