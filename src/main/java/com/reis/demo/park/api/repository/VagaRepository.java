package com.reis.demo.park.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reis.demo.park.api.entity.Vaga;

public interface VagaRepository extends JpaRepository<Vaga, Long>{
    boolean existsByCodigo(String codigo);

    // Busca uma vaga pelo código
    Optional<Vaga> findByCodigo(String codigo);
    
}
