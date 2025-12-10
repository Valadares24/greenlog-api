package com.greenlog.greenlog_solutions_api.model.grafo;

// Usamos @Data para getters/setters e Comparable para o PriorityQueue
import lombok.Data; 

@Data
public class No implements Comparable<No> {
    
    private String nomeBairro;
    private double distancia; // Distância mínima do vértice de origem até este nó
    
    public No(String nomeBairro, double distancia) {
        this.nomeBairro = nomeBairro;
        this.distancia = distancia;
    }
    
    // Implementação crucial para o PriorityQueue de Dijkstra
    @Override
    public int compareTo(No outro) {
        // Compara os nós pela distância (menor distância tem maior prioridade)
        return Double.compare(this.distancia, outro.distancia);
    }
}