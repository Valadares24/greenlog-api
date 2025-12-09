package com.greenlog.greenlog_solutions_api.repository;

import com.greenlog.greenlog_solutions_api.model.entity.ArestaConexao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArestaConexaoRepository extends JpaRepository<ArestaConexao, Long> {

    // Método essencial: buscar todas as arestas que partem de um bairro
    List<ArestaConexao> findByBairroOrigem(String bairroOrigem);
    
    // Método para buscar todas as arestas (útil para construir o grafo na memória)
    // JpaRepository já fornece o findAll()
}