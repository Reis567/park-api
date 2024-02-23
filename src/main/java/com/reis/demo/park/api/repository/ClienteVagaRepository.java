package com.reis.demo.park.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.reis.demo.park.api.entity.ClienteVaga;
import com.reis.demo.park.api.repository.projection.ClienteVagaProjection;

public interface ClienteVagaRepository extends JpaRepository<ClienteVaga, Long>{

    Optional<ClienteVaga> findByReciboAndDataSaidaIsNull(String recibo);

    long countByClienteCpfAndDataSaidaIsNotNull(String cpf);

    Page<ClienteVagaProjection> findAllByClienteCpf(String clienteCPF,Pageable pageable);

    
} 