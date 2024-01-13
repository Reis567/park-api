package com.reis.demo.park.api.web.dto.mapper;

import org.modelmapper.ModelMapper;

import com.reis.demo.park.api.entity.Usuario;
import com.reis.demo.park.api.web.dto.UsuarioCreateDTO;

public class UsuarioMapper {

    public static Usuario toUsuario(UsuarioCreateDTO usuarioCreateDTO){
        return new ModelMapper().map(usuarioCreateDTO, Usuario.class);
    }
}
