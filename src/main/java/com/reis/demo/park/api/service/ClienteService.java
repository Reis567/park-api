package com.reis.demo.park.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reis.demo.park.api.entity.Cliente;
import com.reis.demo.park.api.repository.ClienteRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;

    @Transactional
    public Cliente salvar (Cliente cliente){
        try {
            clienteRepository.save(cliente);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new CpfUniqueViolationException("");
        }
    }
}
