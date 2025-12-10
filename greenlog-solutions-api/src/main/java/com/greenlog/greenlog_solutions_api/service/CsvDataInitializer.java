package com.greenlog.greenlog_solutions_api.service;

import com.greenlog.greenlog_solutions_api.model.entity.ArestaConexao;
import com.greenlog.greenlog_solutions_api.repository.ArestaConexaoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvDataInitializer implements CommandLineRunner {

    private final ArestaConexaoRepository arestaConexaoRepository;
    
    // Injetar o Repository (via construtor)
    public CsvDataInitializer(ArestaConexaoRepository arestaConexaoRepository) {
        this.arestaConexaoRepository = arestaConexaoRepository;
    }

    // Este método é executado ao iniciar a aplicação
    @Override
    public void run(String... args) throws Exception {
        // Verifica se a tabela já está populada para evitar duplicação em cada reinício
        if (arestaConexaoRepository.count() == 0) {
            System.out.println("Iniciando a carga de dados do grafo...");
            carregarArestas();
        } else {
            System.out.println("Dados do grafo já existem. Pulando a carga CSV.");
        }
    }

    private void carregarArestas() {
        // O nome do arquivo CSV de conexões é arestas_conexoes.csv ou ruas_conexoes.csv
        // Vamos usar ruas_conexoes.csv (conforme o link no documento)
        final String CSV_FILE = "/ruas_conexoes.csv"; 
        
        List<ArestaConexao> arestas = new ArrayList<>();

        // O arquivo deve estar na pasta src/main/resources
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream(CSV_FILE), StandardCharsets.UTF_8))) {
            
            String line;
            br.readLine(); // Pula a linha de cabeçalho (Origem, Destino, Distancia)

            while ((line = br.readLine()) != null) {
                // Remove aspas duplas, se houver
                String[] values = line.split(","); 
                
                if (values.length >= 3) {
                    try {
                        String origem = values[0].trim();
                        String destino = values[1].trim();
                        // Tenta converter a distância, removendo "km" se estiver presente
                        double distancia = Double.parseDouble(values[2].trim().replaceAll("[^\\d.]", "")); 

                        ArestaConexao aresta = new ArestaConexao(
                            null, // ID será gerado automaticamente
                            origem,
                            destino,
                            distancia
                        );
                        arestas.add(aresta);
                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao processar linha (distância inválida): " + line);
                    }
                }
            }
            
            // Salva todas as entidades no banco de dados de uma vez (batch save)
            arestaConexaoRepository.saveAll(arestas);
            System.out.println("Carga de " + arestas.size() + " arestas de conexão concluída.");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Falha ao carregar o arquivo CSV: " + CSV_FILE);
        }
    }
}