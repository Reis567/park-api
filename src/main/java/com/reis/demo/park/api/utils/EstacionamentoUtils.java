package com.reis.demo.park.api.utils;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EstacionamentoUtils {
    public static String gerarRecibo(){
        LocalDateTime date = LocalDateTime.now();

        String recibo = date.toString().substring(0,19);

        return recibo.replace("-", "")
                        .replace(":","")
                        .replace("T","");
    }
}
