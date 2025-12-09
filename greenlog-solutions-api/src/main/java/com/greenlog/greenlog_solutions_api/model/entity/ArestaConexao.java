package com.greenlog.greenlog_solutions_api.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "arestas_conexoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArestaConexao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @Column(nullable = false)
    private String bairroOrigem; // Vértice de partida

    @Column(nullable = false)
    private String bairroDestino; // Vértice de chegada

    // Distância em km (o peso da aresta)
    @Column(nullable = false)
    private Double distanciaKm; 
}