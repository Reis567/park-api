package com.reis.demo.park.api.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reis.demo.park.api.entity.Cliente;
import com.reis.demo.park.api.entity.ClienteVaga;
import com.reis.demo.park.api.entity.Vaga;
import com.reis.demo.park.api.utils.EstacionamentoUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EstacionamentoService {

    private final ClienteVagaService clienteVagaService;

    private final ClienteService clienteService;

    private final VagaService vagaService;

    @Transactional
    public ClienteVaga checkIn(ClienteVaga clienteVaga) {
        log.info("Iniciando check-in");
        Cliente cliente = clienteService.buscarPorCpf(clienteVaga.getCliente().getCpf());
        log.info("Cliente encontrado: {}", cliente);
        clienteVaga.setCliente(cliente);
        Vaga vaga = vagaService.buscarPorVagaLivre();
        log.info("Vaga encontrada: {}", vaga);
        vaga.setStatus(Vaga.StatusVaga.OCUPADA);
        clienteVaga.setVaga(vaga);

        clienteVaga.setDataEntrada(LocalDateTime.now());
        clienteVaga.setRecibo(EstacionamentoUtils.gerarRecibo());

        return clienteVagaService.salvar(clienteVaga);
    }

    @Transactional
    public ClienteVaga checkOut(String recibo) {
        ClienteVaga clienteVaga = clienteVagaService.buscarPorRecibo(recibo);

        LocalDateTime dataSaida = LocalDateTime.now();

        BigDecimal valor = EstacionamentoUtils.calcularCustoEstacionamento(clienteVaga.getDataEntrada(), dataSaida);

        clienteVaga.setValor(valor);

        long totalDeVezes = clienteVagaService.getTotalVezesEstacionamentoCompleto(clienteVaga.getCliente().getCpf());

        BigDecimal desconto = EstacionamentoUtils.calcularDesconto(valor, totalDeVezes);
        clienteVaga.setDesconto(desconto);

        clienteVaga.setDataSaida(dataSaida);

        clienteVaga.getVaga().setStatus(Vaga.StatusVaga.LIVRE);
        return clienteVagaService.salvar(clienteVaga);
    }

}
