package com.reis.demo.park.api.web.dto;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EstacionamentoResponseDTO {
    
    private String placa;

    private String marca;


    private String modelo;


    private String cor;

    private String clienteCpf;

    private String recibo;

    private LocalDateTime dataEntrada;
    
    private LocalDateTime dataSaida;

    private String vagaCodigo;

    private BigDecimal valor;

    private BigDecimal desconto;
}