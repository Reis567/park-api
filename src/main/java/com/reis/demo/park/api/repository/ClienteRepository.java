package com.reis.demo.park.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reis.demo.park.api.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    
}
