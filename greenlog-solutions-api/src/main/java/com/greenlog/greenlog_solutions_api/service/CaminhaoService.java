package com.greenlog.greenlog_solutions_api.service;

import com.greenlog.greenlog_solutions_api.dto.CaminhaoRequest;
import com.greenlog.greenlog_solutions_api.dto.CaminhaoResponse;
import com.greenlog.greenlog_solutions_api.model.entity.Caminhao;
import com.greenlog.greenlog_solutions_api.repository.CaminhaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // Cria um construtor com os campos finais (Injeção de Dependência)
public class CaminhaoService {

    private final CaminhaoRepository caminhaoRepository;

    // --- Mapeamento DTO -> Entity (Função auxiliar) ---
    private Caminhao mapToEntity(CaminhaoRequest request) {
        return new Caminhao(
            request.getPlaca(),
            request.getNomeMotorista(),
            request.getCapacidadeMaximaKg(),
            request.getTiposResiduosHabilitados()
        );
    }

    // --- Mapeamento Entity -> DTO (Função auxiliar) ---
    private CaminhaoResponse mapToResponse(Caminhao caminhao) {
        return new CaminhaoResponse(
            caminhao.getPlaca(),
            caminhao.getNomeMotorista(),
            caminhao.getCapacidadeMaximaKg(),
            caminhao.getTiposResiduosHabilitados()
        );
    }

    // --- CREATE (Criação) ---
    @Transactional
    public CaminhaoResponse create(CaminhaoRequest request) {
        // Validação da Regra de Negócio: Placa deve ser única
        if (caminhaoRepository.findByPlaca(request.getPlaca()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Caminhão com placa " + request.getPlaca() + " já existe.");
        }
        
        Caminhao caminhao = mapToEntity(request);
        Caminhao savedCaminhao = caminhaoRepository.save(caminhao);
        return mapToResponse(savedCaminhao);
    }

    // --- READ (Leitura de todos) ---
    @Transactional(readOnly = true)
    public List<CaminhaoResponse> findAll() {
        return caminhaoRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // --- READ (Leitura por ID/Placa) ---
    @Transactional(readOnly = true)
    public CaminhaoResponse findByPlaca(String placa) {
        Caminhao caminhao = caminhaoRepository.findByPlaca(placa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Caminhão não encontrado."));
        return mapToResponse(caminhao);
    }

    // --- UPDATE (Atualização) ---
    @Transactional
    public CaminhaoResponse update(String placa, CaminhaoRequest request) {
        // Busca o caminhão existente
        Caminhao caminhao = caminhaoRepository.findByPlaca(placa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Caminhão não encontrado."));

        // Nota: A placa não é alterada neste método (apenas os outros campos)
        caminhao.setNomeMotorista(request.getNomeMotorista());
        caminhao.setCapacidadeMaximaKg(request.getCapacidadeMaximaKg());
        caminhao.setTiposResiduosHabilitados(request.getTiposResiduosHabilitados());
        
        Caminhao updatedCaminhao = caminhaoRepository.save(caminhao); // O save atualiza o registro existente
        return mapToResponse(updatedCaminhao);
    }

    // --- DELETE (Exclusão) ---
    @Transactional
    public void delete(String placa) {
        // Verifica se o caminhão existe antes de deletar
        if (!caminhaoRepository.findByPlaca(placa).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Caminhão não encontrado para exclusão.");
        }
        // Nota: Regra de Negócio 7 (Entidades relacionadas não podem ser excluídas)
        // será implementada aqui em fases futuras.
        
        caminhaoRepository.deleteById(placa);
    }
}