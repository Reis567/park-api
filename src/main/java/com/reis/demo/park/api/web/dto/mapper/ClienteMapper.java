package com.reis.demo.park.api.web.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

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
    public static List<ClienteResponseDTO> toDTOList(List<Cliente> clientes) {
        return clientes.stream()
                .map(ClienteMapper::toDTO)
                .collect(Collectors.toList());
    }
}
