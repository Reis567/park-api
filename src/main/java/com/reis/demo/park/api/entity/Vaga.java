package com.reis.demo.park.api.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
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

    @CreatedDate
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
    
    @LastModifiedDate
    @Column(name = "data_modificacao")
    private LocalDateTime dataModificacao;
    
    @CreatedBy
    @Column(name = "criado_por")
    private String criadoPor;
    
    @LastModifiedBy
    @Column(name = "modificado_por")
    private String modificadoPor;

    
}
