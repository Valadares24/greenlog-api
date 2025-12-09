package com.greenlog.greenlog_solutions_api.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "pontos_coleta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PontoColeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    // O nome do ponto de coleta deve ser único [cite: 111, 113]
    @Column(unique = true, nullable = false)
    private String nome; // Nome do ponto de coleta (ex: Jardim das Flores, Setor Sul) [cite: 74]

    @Column(nullable = false)
    private String nomeResponsavel; // Nome do responsável direto [cite: 75]

    @Column(nullable = false)
    private String contato; // Informações de contato [cite: 76]

    @Column(nullable = false)
    private String endereco; // Endereço completo [cite: 111]
    
    // O bairro é crucial, pois é o vértice no grafo [cite: 82]
    @Column(nullable = false)
    private String bairro; 

    // Lista de tipos de resíduos aceitos naquele ponto (pode haver mais de um) [cite: 78, 112]
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "ponto_residuos", joinColumns = @JoinColumn(name = "ponto_id"))
    @Column(name = "tipo_residuo", nullable = false)
    private Set<String> tiposResiduosAceitos; 
}