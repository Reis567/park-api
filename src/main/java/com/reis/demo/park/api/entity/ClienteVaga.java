package com.reis.demo.park.api.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import org.springframework.data.annotation.*;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Table(name = "clientes_tem_vagas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ClienteVaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_recibo", nullable = false, unique = true, length = 15)
    private String rebibo;

    @Column(name = "placa" ,nullable = false,length = 8)
    private String placa;

    @Column(name = "marca",nullable = false,length = 45)
    private String marca;
    
    @Column(name = "modelo",nullable = false,length = 45)
    private String modelo;

    @Column(name = "cor",nullable = false,length = 45)
    private String cor;

    @Column(name = "data_entrada",nullable = false)
    private LocalDateTime dataEntrada;

    @Column(name = "data_saida",nullable = true)
    private LocalDateTime dataSaida;

    @Column(name = "valor",columnDefinition = "decimal(7,2)")
    private BigDecimal valor;

    @Column(name = "desconto",columnDefinition = "decimal(7,2)")
    private BigDecimal desconto;

    @ManyToOne
    @JoinColumn(name = "id_cliente",nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_vaga",nullable = false)
    private Vaga vaga;

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
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }


}
