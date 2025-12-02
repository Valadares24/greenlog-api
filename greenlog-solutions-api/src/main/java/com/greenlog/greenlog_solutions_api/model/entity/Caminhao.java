package com.greenlog.greenlog_solutions_api.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "caminhoes")
@Data // Inclui Getter, Setter, toString, equals, hashCode (Lombok)
@NoArgsConstructor
@AllArgsConstructor
public class Caminhao {

    // A placa é a identificação única e servirá como chave primária.
    @Id
    // Define a placa no formato AAAIA11 (Mercosul) ou outro formato REGEX validado depois
    // Usamos @Column para garantir que a coluna seja única e não nula.
    @Column(length = 7, unique = true, nullable = false) 
    private String placa; // Identificação (placa) 

    @Column(nullable = false)
    private String nomeMotorista; // Nome do motorista responsável 

    @Column(nullable = false)
    private Double capacidadeMaximaKg; // Capacidade máxima de carga (em kg ou volume estimado)

    // Este campo representa os tipos de resíduos que o caminhão pode transportar.
    // Usamos @ElementCollection para armazenar a lista de strings (tipos de resíduos).
    // Geralmente mapeado para uma tabela separada para a relação 1:N no BD.
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "caminhao_residuos", joinColumns = @JoinColumn(name = "caminhao_placa"))
    @Column(name = "tipo_residuo", nullable = false)
    private Set<String> tiposResiduosHabilitados; 
    // Ex: "plástico", "papel", "metal", "orgânico" [cite: 69]

    // Nota: Usamos Set<String> para garantir que cada tipo de resíduo seja único para o caminhão.
}