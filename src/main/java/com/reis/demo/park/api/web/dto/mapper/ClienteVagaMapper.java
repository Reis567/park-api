package com.reis.demo.park.api.web.dto.mapper;

import org.modelmapper.ModelMapper;

import com.reis.demo.park.api.entity.ClienteVaga;
import com.reis.demo.park.api.web.dto.EstacionamentoCreateDTO;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteVagaMapper {
    public static ClienteVaga toClienteVaga(EstacionamentoCreateDTO estacionamentoCreateDTO){
        return new ModelMapper().map(estacionamentoCreateDTO, ClienteVaga.class);
    }
}
