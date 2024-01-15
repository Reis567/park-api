package com.reis.demo.park.api.web.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import com.reis.demo.park.api.entity.Usuario;
import com.reis.demo.park.api.web.dto.UsuarioCreateDTO;
import com.reis.demo.park.api.web.dto.UsuarioResponseDTO;

public class UsuarioMapper {

    public static Usuario toUsuario(UsuarioCreateDTO usuarioCreateDTO){
        return new ModelMapper().map(usuarioCreateDTO, Usuario.class);
    }

    public static UsuarioResponseDTO toDTO(Usuario usuario){
        String role = usuario.getRole().name().substring("ROLE_".length());
        PropertyMap<Usuario,UsuarioResponseDTO> propertyMap= new PropertyMap<Usuario,UsuarioResponseDTO>() {
            @Override
            protected void configure(){
                map().setRole(role);
            }
        };

        ModelMapper modelMapper =  new ModelMapper();
        modelMapper.addMappings(propertyMap);
        return modelMapper.map(usuario, UsuarioResponseDTO.class);

    }

    public static List<UsuarioResponseDTO> toListDTO(List<Usuario> usuarioList){
        return usuarioList.stream().map(user -> toDTO(user)).collect(Collectors.toList());
    }
}
