package com.greenlog.greenlog_solutions_api.repository;



import com.greenlog.greenlog_solutions_api.model.entity.Caminhao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CaminhaoRepository extends JpaRepository<Caminhao, String> {

    // Método customizado para buscar um caminhão pela placa, 
    // que é útil para as validações de unicidade.
    Optional<Caminhao> findByPlaca(String placa);

    // O JpaRepository já fornece: save, findById, findAll, deleteById, etc.
}