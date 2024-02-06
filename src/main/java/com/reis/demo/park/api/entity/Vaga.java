package com.reis.demo.park.api.entity;

import java.io.Serializable;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;

@Entity
@Table(name="vagas")
@EntityListeners(AuditingEntityListener.class)
public class Vaga implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "codigo", nullable = false, unique = true,length = 4)
    private String codigo;

    @Column(name="status",nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusVaga status;

    public enum StatusVaga {
        LIVRE, 
        OCUPADA
    }
    
}
