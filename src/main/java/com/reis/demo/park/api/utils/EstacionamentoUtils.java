package com.reis.demo.park.api.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EstacionamentoUtils {

    private static final double PRIMEIROS_15_MINUTOS = 5.00;
    private static final double PRIMEIROS_60_MINUTOS = 9.25;
    private static final double ADICIONAL_15_MINUTOS = 1.75;

    public static String gerarRecibo(){
        LocalDateTime date = LocalDateTime.now();

        String recibo = date.toString().substring(0,19);

        return recibo.replace("-", "")
                        .replace(":","")
                        .replace("T","-");
    }



    public static BigDecimal calcularCustoEstacionamento(LocalDateTime entrada, LocalDateTime saida) {
        Duration duracao = Duration.between(entrada, saida);
        long minutosTotais = duracao.toMinutes();

        double total = 0.0;

        if (minutosTotais <= 15) {
            total = PRIMEIROS_15_MINUTOS;
        } else if (minutosTotais <= 60) {
            total = PRIMEIROS_60_MINUTOS;
        } else {
            long minutosAdicionais = minutosTotais - 60;
            total = PRIMEIROS_60_MINUTOS + Math.ceil((double) minutosAdicionais / 15) * ADICIONAL_15_MINUTOS;
        }

        return new BigDecimal(total).setScale(2, RoundingMode.HALF_EVEN);
    }
}
