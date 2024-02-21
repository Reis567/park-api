package com.reis.demo.park.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reis.demo.park.api.entity.ClienteVaga;

public interface ClienteVagaRepository extends JpaRepository<ClienteVaga, Long>{

    Optional<ClienteVaga> findByReciboAndDataSaidaIsNull(String recibo);

    long countByClienteCpfAndDataSaidaIsNotNull(String cpf);

    
} 