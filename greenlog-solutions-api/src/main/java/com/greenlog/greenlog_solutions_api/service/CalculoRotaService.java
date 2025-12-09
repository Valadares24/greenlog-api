package com.greenlog.greenlog_solutions_api.service;

import com.greenlog.greenlog_solutions_api.dto.RotaResponse;
import com.greenlog.greenlog_solutions_api.model.entity.ArestaConexao;
import com.greenlog.greenlog_solutions_api.model.grafo.No; // Classe No criada anteriormente
import com.greenlog.greenlog_solutions_api.repository.ArestaConexaoRepository;
import jakarta.annotation.PostConstruct; 
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalculoRotaService {

    private final ArestaConexaoRepository arestaConexaoRepository;
    
    // Mapa de adjacência do grafo (BairroOrigem -> Lista de Arestas que saem dele)
    private Map<String, List<ArestaConexao>> grafo;
    
    // Conjunto de todos os bairros únicos (vértices)
    private Set<String> todosOsBairros; 

    /**
     * Padrão Singleton: Carrega e constrói o grafo na memória apenas uma vez.
     * Necessita que todos os dados de ArestaConexao já estejam no banco (via importação de CSV).
     */
    @PostConstruct
    public void inicializarGrafo() {
        List<ArestaConexao> todasArestas = arestaConexaoRepository.findAll();
        
        grafo = new HashMap<>();
        todosOsBairros = new HashSet<>();
        
        for (ArestaConexao aresta : todasArestas) {
            String origem = aresta.getBairroOrigem();
            String destino = aresta.getBairroDestino();
            
            // Adicionar Origem -> Destino
            grafo.computeIfAbsent(origem, k -> new ArrayList<>()).add(aresta);
            todosOsBairros.add(origem);

            // Adicionar Destino -> Origem (Garantindo que a malha viária seja bidirecional)
            ArestaConexao reversa = new ArestaConexao(null, destino, origem, aresta.getDistanciaKm());
            grafo.computeIfAbsent(destino, k -> new ArrayList<>()).add(reversa);
            todosOsBairros.add(destino);
        }
    }

    /**
     * Implementação do Algoritmo de Dijkstra para encontrar o menor caminho.
     * @return RotaResponse contendo o caminho e a distância total.
     */
    public RotaResponse calcularMenorRota(String origem, String destino) {
        if (!todosOsBairros.contains(origem) || !todosOsBairros.contains(destino)) {
             return new RotaResponse(origem, destino, Collections.emptyList(), 0.0, "BAIRRO_INEXISTENTE");
        }
        
        Map<String, Double> distancias = new HashMap<>(); 
        Map<String, String> predecessores = new HashMap<>(); 
        PriorityQueue<No> filaPrioridade = new PriorityQueue<>(); 
        
        // 1. Inicialização
        todosOsBairros.forEach(bairro -> distancias.put(bairro, Double.MAX_VALUE));
        distancias.put(origem, 0.0);
        filaPrioridade.add(new No(origem, 0.0));
        
        // 2. Execução do Algoritmo de Dijkstra
        while (!filaPrioridade.isEmpty()) {
            No noAtual = filaPrioridade.poll();
            
            if (noAtual.getDistancia() > distancias.getOrDefault(noAtual.getNomeBairro(), Double.MAX_VALUE)) {
                continue;
            }

            for (ArestaConexao aresta : grafo.getOrDefault(noAtual.getNomeBairro(), Collections.emptyList())) {
                double novaDistancia = noAtual.getDistancia() + aresta.getDistanciaKm();
                String vizinho = aresta.getBairroDestino();

                // Relaxamento
                if (novaDistancia < distancias.getOrDefault(vizinho, Double.MAX_VALUE)) {
                    distancias.put(vizinho, novaDistancia);
                    predecessores.put(vizinho, noAtual.getNomeBairro());
                    filaPrioridade.add(new No(vizinho, novaDistancia));
                }
            }
        }
        
        // 3. Montagem da Resposta (converte resultados do cálculo para o DTO)
        return montarRotaResponse(origem, destino, distancias, predecessores);
    }
    
    // --- MÉTODOS AUXILIARES ---

    private RotaResponse montarRotaResponse(String origem, String destino, Map<String, Double> distancias, Map<String, String> predecessores) {
        
        double distanciaTotal = distancias.getOrDefault(destino, Double.MAX_VALUE);
        
        // Se a distância for MAX_VALUE, o nó é inalcançável.
        if (distanciaTotal == Double.MAX_VALUE) {
            return new RotaResponse(origem, destino, Collections.emptyList(), 0.0, "INACESSÍVEL");
        }
        
        List<String> caminho = reconstruirCaminho(predecessores, destino, origem);
        
        return new RotaResponse(
            origem,
            destino,
            caminho,
            distanciaTotal,
            "SUCESSO"
        );
    }

    // Reconstroi o caminho do destino para a origem (de trás para frente)
    private List<String> reconstruirCaminho(Map<String, String> predecessores, String destino, String origem) {
        LinkedList<String> caminho = new LinkedList<>();
        String passo = destino;
        
        // Enquanto houver um predecessor, adiciona o nó ao início da lista (caminho reverso)
        while (passo != null) {
            caminho.addFirst(passo);
            // Se chegamos na origem e ela está no início da lista, paramos.
            if (passo.equals(origem)) {
                break;
            }
            passo = predecessores.get(passo);
        }
        
        // Verifica se a reconstrução foi bem-sucedida (o primeiro nó é a origem)
        if (caminho.isEmpty() || !caminho.getFirst().equals(origem)) {
            return Collections.emptyList();
        }
        
        return caminho;
    }
}