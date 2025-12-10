package com.greenlog.greenlog_solutions_api.repository;

import com.greenlog.greenlog_solutions_api.model.entity.PontoColeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface PontoColetaRepository extends JpaRepository<PontoColeta, Long> {

    Optional<PontoColeta> findByNome(String nome);
    
    List<PontoColeta> findByBairro(String bairro);

    
    
    List<PontoColeta> findByTiposResiduosAceitosContaining(String tipoResiduo);
}