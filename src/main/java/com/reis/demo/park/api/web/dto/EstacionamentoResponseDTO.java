package com.reis.demo.park.api.web.dto;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EstacionamentoResponseDTO {
    
    private String placa;

    private String marca;


    private String modelo;


    private String cor;

    private String clienteCpf;

    private String recibo;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime dataEntrada;
    
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime dataSaida;

    private String vagaCodigo;

    private BigDecimal valor;

    private BigDecimal desconto;
}
