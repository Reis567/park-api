package com.reis.demo.park.api.entity;

import java.io.Serializable;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;

@Entity
@Table(name="vagas")
@EntityListeners(AuditingEntityListener.class)
public class Vaga implements Serializable{
    
}
