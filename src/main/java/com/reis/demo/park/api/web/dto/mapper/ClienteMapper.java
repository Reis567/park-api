package com.reis.demo.park.api.web.dto.mapper;

import org.modelmapper.ModelMapper;

import com.reis.demo.park.api.entity.Cliente;
import com.reis.demo.park.api.web.dto.ClienteCreateDTO;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteMapper {
    
    public static Cliente toCliente(ClienteCreateDTO clienteCreateDTO){
        return new ModelMapper().map(clienteCreateDTO, Cliente.class);
    }
}
