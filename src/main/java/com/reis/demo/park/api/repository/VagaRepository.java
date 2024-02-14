package com.reis.demo.park.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reis.demo.park.api.entity.Vaga;

public interface VagaRepository extends JpaRepository<Vaga, Long>{
    
    Optional<Vaga> findByCodigo(String codigo);

    Optional<Vaga> findFirstByStatus(Vaga.StatusVaga  statusVaga);
    
}
