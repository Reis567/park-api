package com.reis.demo.park.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.reis.demo.park.api.entity.ClienteVaga;
import com.reis.demo.park.api.exception.EntityNotFoundException;
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
            // clienteVaga.setRecibo(EstacionamentoUtils.gerarRecibo());

            log.info("Salvando clienteVaga: {}", clienteVaga);
            return clienteVagaRepository.save(clienteVaga);
        } catch (Exception ex) {
            log.error("Erro ao salvar clienteVaga. Motivo: {}", ex.getMessage());
            throw ex;
        }
    }

    @Transactional(readOnly = true)
    public ClienteVaga buscarPorRecibo(String recibo) {
        return clienteVagaRepository.findByReciboAndDataSaidaIsNull(recibo).orElseThrow(
            ()-> new EntityNotFoundException(
                String.format("Recibo '%s' não encontrado no sistema ou check-out já realizado",recibo)
            )
        );
    }

    @Transactional(readOnly = true)
    public long getTotalVezesEstacionamentoCompleto(String cpf) {
        return clienteVagaRepository.countByClienteCpfAndDataSaidaIsNotNull(cpf);
    }


    @Transactional(readOnly = true)
    public List<ClienteVaga> getUsosDeEstacionamentoPorCPF(String clienteCPF) {

        return clienteVagaRepository.findByClienteCpfAndDataSaidaIsNotNull(clienteCPF);
    }
}
