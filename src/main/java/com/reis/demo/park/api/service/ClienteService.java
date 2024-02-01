package com.reis.demo.park.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reis.demo.park.api.entity.Cliente;
import com.reis.demo.park.api.exception.CpfUniqueViolationException;
import com.reis.demo.park.api.repository.ClienteRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;

    @Transactional
    public Cliente salvar(Cliente cliente) {
        try {
            log.info("Salvando cliente: {}", cliente);
            Cliente savedCliente = clienteRepository.save(cliente);
            log.info("Cliente salvo com sucesso: {}", savedCliente);
            return savedCliente;
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            log.error("Erro ao salvar cliente. Motivo: {}", ex.getMessage());
            throw new CpfUniqueViolationException(String.format("CPF '%s' não pode ser cadastrado, já existe", cliente.getCpf()));
        }
    }
}
