package com.greenlog.greenlog_solutions_api.service;

import com.greenlog.greenlog_solutions_api.dto.RotaResponse;
import com.greenlog.greenlog_solutions_api.model.entity.ArestaConexao;
import com.greenlog.greenlog_solutions_api.model.grafo.No;
import com.greenlog.greenlog_solutions_api.repository.ArestaConexaoRepository;
import jakarta.annotation.PostConstruct; 
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CalculoRotaService {

    private final ArestaConexaoRepository arestaConexaoRepository;
    
    private Map<String, List<ArestaConexao>> grafo;
    private Set<String> todosOsBairros; 

    @PostConstruct
    public void inicializarGrafo() {
        List<ArestaConexao> todasArestas = arestaConexaoRepository.findAll();
        
        grafo = new HashMap<>();
        todosOsBairros = new HashSet<>();
        
        for (ArestaConexao aresta : todasArestas) {
            String origem = aresta.getBairroOrigem();
            String destino = aresta.getBairroDestino();
            
            grafo.computeIfAbsent(origem, k -> new ArrayList<>()).add(aresta);
            todosOsBairros.add(origem);

            ArestaConexao reversa = new ArestaConexao(null, destino, origem, aresta.getDistanciaKm());
            grafo.computeIfAbsent(destino, k -> new ArrayList<>()).add(reversa);
            todosOsBairros.add(destino);
        }
    }

    public RotaResponse calcularMenorRota(String origem, String destino) {
        if (!todosOsBairros.contains(origem) || !todosOsBairros.contains(destino)) {
             return new RotaResponse(null, "BAIRRO_INEXISTENTE", null, 0.0, Collections.emptyList(), Collections.emptySet());
        }
        
        Map<String, Double> distancias = new HashMap<>(); 
        Map<String, String> predecessores = new HashMap<>(); 
        PriorityQueue<No> filaPrioridade = new PriorityQueue<>(); 
        
        todosOsBairros.forEach(bairro -> distancias.put(bairro, Double.MAX_VALUE));
        distancias.put(origem, 0.0);
        filaPrioridade.add(new No(origem, 0.0));
        
        while (!filaPrioridade.isEmpty()) {
            No noAtual = filaPrioridade.poll();
            
            if (noAtual.getDistancia() > distancias.getOrDefault(noAtual.getNomeBairro(), Double.MAX_VALUE)) {
                continue;
            }

            for (ArestaConexao aresta : grafo.getOrDefault(noAtual.getNomeBairro(), Collections.emptyList())) {
                double novaDistancia = noAtual.getDistancia() + aresta.getDistanciaKm();
                String vizinho = aresta.getBairroDestino();

                if (novaDistancia < distancias.getOrDefault(vizinho, Double.MAX_VALUE)) {
                    distancias.put(vizinho, novaDistancia);
                    predecessores.put(vizinho, noAtual.getNomeBairro());
                    filaPrioridade.add(new No(vizinho, novaDistancia));
                }
            }
        }
        
        return montarRotaResponse(origem, destino, distancias, predecessores);
    }

    private RotaResponse montarRotaResponse(String origem, String destino, Map<String, Double> distancias, Map<String, String> predecessores) {
        
        double distanciaTotal = distancias.getOrDefault(destino, Double.MAX_VALUE);
        
        if (distanciaTotal == Double.MAX_VALUE) {
            return new RotaResponse(null, "INACESSÍVEL", null, 0.0, Collections.emptyList(), Collections.emptySet());
        }
        
        List<String> caminho = reconstruirCaminho(predecessores, destino, origem);
        
        return new RotaResponse(
            null,
            "Rota " + origem + " -> " + destino,
            null,
            distanciaTotal,
            caminho,
            Collections.emptySet()
        );
    }

    private List<String> reconstruirCaminho(Map<String, String> predecessores, String destino, String origem) {
        LinkedList<String> caminho = new LinkedList<>();
        String passo = destino;
        
        while (passo != null) {
            caminho.addFirst(passo);
            if (passo.equals(origem)) {
                break;
            }
            passo = predecessores.get(passo);
        }
        
        if (caminho.isEmpty() || !caminho.getFirst().equals(origem)) {
            return Collections.emptyList();
        }
        
        return caminho;
    }
}
