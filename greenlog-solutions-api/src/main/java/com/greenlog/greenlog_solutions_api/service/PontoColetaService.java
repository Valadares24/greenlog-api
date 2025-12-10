package com.greenlog.greenlog_solutions_api.service;

import com.greenlog.greenlog_solutions_api.dto.PontoColetaRequest;
import com.greenlog.greenlog_solutions_api.dto.PontoColetaResponse;
import com.greenlog.greenlog_solutions_api.model.entity.PontoColeta;
import com.greenlog.greenlog_solutions_api.repository.PontoColetaRepository;
// ... (imports necessários)
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PontoColetaService {

    private final PontoColetaRepository pontoColetaRepository;
    
    // Funções mapToEntity/mapToResponse (omitas para brevidade, mas devem ser implementadas)

    // --- CREATE ---
    @Transactional
    public PontoColetaResponse create(PontoColetaRequest request) {
        // Regra de Negócio: Não é permitida a duplicidade de nome de ponto 
        if (pontoColetaRepository.findByNome(request.getNome()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ponto de coleta com nome " + request.getNome() + " já existe.");
        }
        
        PontoColeta ponto = mapToEntity(request);
        return mapToResponse(pontoColetaRepository.save(ponto));
    }

    // --- READ ALL ---
    @Transactional(readOnly = true)
    public List<PontoColetaResponse> findAll() {
        return pontoColetaRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    // --- UPDATE ---
    @Transactional
    public PontoColetaResponse update(Long id, PontoColetaRequest request) {
        PontoColeta ponto = pontoColetaRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ponto de coleta não encontrado."));

    // Previne alteração do nome se já existir outro ponto com o novo nome
    if (!ponto.getNome().equals(request.getNome()) && pontoColetaRepository.findByNome(request.getNome()).isPresent()) {
         throw new ResponseStatusException(HttpStatus.CONFLICT, "Nome de ponto de coleta já em uso.");
    }

    ponto.setNome(request.getNome());
    ponto.setNomeResponsavel(request.getNomeResponsavel()); 
    ponto.setContato(request.getContato());               
    ponto.setEndereco(request.getEndereco());             
    ponto.setBairro(request.getBairro());                 
    ponto.setTiposResiduosAceitos(request.getTiposResiduosAceitos()); 
    
    return mapToResponse(pontoColetaRepository.save(ponto));
    }

    // --- DELETE ---
    @Transactional
    public void delete(Long id) {
        if (!pontoColetaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ponto de coleta não encontrado para exclusão.");
        }
        // Nota: Regra de Negócio 7 (Entidades relacionadas...) será implementada aqui em fases futuras.
        pontoColetaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
public List<PontoColetaResponse> findByBairro(String bairro) {
    // Busca todas as entidades (PontoColeta) que pertencem ao bairro
    List<PontoColeta> pontos = pontoColetaRepository.findByBairro(bairro);
    
    // Mapeia cada Entity para um DTO e coleta em uma lista
    return pontos.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
}

@Transactional(readOnly = true)
public PontoColetaResponse findById(Long id) {
    // Busca o ID no Repository (herdado do JpaRepository)
    PontoColeta pontoColeta = pontoColetaRepository.findById(id)
            // Se não encontrar, lança exceção 404 (tratada pelo Spring)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ponto de coleta não encontrado."));
            
    // Converte a Entity para o DTO de Resposta
    return mapToResponse(pontoColeta);
}

    
    // --- Implementação do Mapeamento (Exemplo) ---
    private PontoColeta mapToEntity(PontoColetaRequest request) {
        return new PontoColeta(
            null, // ID será gerado automaticamente
            request.getNome(),
            request.getNomeResponsavel(),
            request.getContato(),
            request.getEndereco(),
            request.getBairro(),
            request.getTiposResiduosAceitos()
        );
    }

    private PontoColetaResponse mapToResponse(PontoColeta ponto) {
        return new PontoColetaResponse(
            ponto.getId(),
            ponto.getNome(),
            ponto.getNomeResponsavel(),
            ponto.getContato(),
            ponto.getEndereco(),
            ponto.getBairro(),
            ponto.getTiposResiduosAceitos()
        );
    }
}