package com.reis.demo.park.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reis.demo.park.api.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario , Long> {
    
}
