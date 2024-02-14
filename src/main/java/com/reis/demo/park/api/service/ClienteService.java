package com.reis.demo.park.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reis.demo.park.api.entity.Cliente;
import com.reis.demo.park.api.exception.CpfUniqueViolationException;
import com.reis.demo.park.api.exception.EntityNotFoundException;
import com.reis.demo.park.api.repository.ClienteRepository;
import com.reis.demo.park.api.repository.projection.ClienteProjection;

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
    @Transactional(readOnly = true)
    public Cliente buscarPorId(Long id) {
        log.info("Buscando cliente por ID: {}", id);
        return clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o ID: " + id));
    }

    @Transactional(readOnly = true)
    public Page<ClienteProjection> buscarTodos(Pageable pageable) {
        log.info("Buscando todos os clientes");
        return clienteRepository.findAllPageable(pageable);
        
    }
    @Transactional(readOnly = true)
    public Cliente buscarPorUsuarioId(Long id) {
        return clienteRepository.findByUsuarioId(id);
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorCpf(String cpf) {
        log.info("Buscando cliente por CPF: {}", cpf);
        return clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o CPF: " + cpf));
    }
}
