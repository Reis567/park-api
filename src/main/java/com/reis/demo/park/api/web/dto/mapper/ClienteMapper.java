package com.reis.demo.park.api.web.dto.mapper;

import org.modelmapper.ModelMapper;

import com.reis.demo.park.api.entity.Cliente;
import com.reis.demo.park.api.web.dto.ClienteCreateDTO;
import com.reis.demo.park.api.web.dto.ClienteResponseDTO;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteMapper {
    
    public static Cliente toCliente(ClienteCreateDTO clienteCreateDTO){
        return new ModelMapper().map(clienteCreateDTO, Cliente.class);
    }
    public static ClienteResponseDTO toDTO(Cliente cliente){
        return new ModelMapper().map(cliente, ClienteResponseDTO.class);
    }
}
