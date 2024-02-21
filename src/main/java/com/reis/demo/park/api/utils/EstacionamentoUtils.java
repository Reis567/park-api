package com.reis.demo.park.api.utils;

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
}
