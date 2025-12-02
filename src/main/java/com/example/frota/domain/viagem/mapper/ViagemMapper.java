package com.example.frota.domain.viagem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.example.frota.application.dto.caminhao.AtualizacaoCaminhao;
import com.example.frota.application.dto.viagem.AtualizacaoViagem;
import com.example.frota.domain.caminhao.model.Caminhao;
import com.example.frota.domain.motorista.model.Motorista;
import com.example.frota.domain.solicitacao.model.Solicitacao;
import com.example.frota.domain.viagem.model.Viagem;

@Mapper(componentModel = "spring")
public interface ViagemMapper {
    
    // Converte Entity para DTO (para preencher formulário de edição)
    @Mapping(target = "caminhaoId", source = "caminhao.id")
    @Mapping(target = "motoristaId", source = "motorista.id")
    //@Mapping(target = "entregas", source = "solicitacao.id")
    AtualizacaoViagem toAtualizacaoDto(Viagem viagem);
    
    // Converte DTO para Entity (para criação NOVA - ignora ID)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "caminhao", source = "caminhaoId", qualifiedByName = "idToCaminhao")
    @Mapping(target = "motorista", source = "motoristaId", qualifiedByName = "idToMotorista")
    //@Mapping(target = "entregas", source = "solicitacaoId", qualifiedByName = "idToSolicitacao")
    Viagem toEntityFromAtualizacao(AtualizacaoViagem dto);
    
    // Atualiza Entity existente com dados do DTO
    @Mapping(target = "id", ignore = true) // Não atualiza ID
    @Mapping(target = "caminhao", source = "caminhaoId", qualifiedByName = "idToCaminhao")
    @Mapping(target = "motorista", source = "motoristaId", qualifiedByName = "idToMotorista")
    //@Mapping(target = "entregas", source = "solicitacaoId", qualifiedByName = "idToSolicitacao")
    void updateEntityFromDto(AtualizacaoViagem dto, @MappingTarget Viagem caminhao);
    
    // Método para converter marcaId em objeto Marca
    @Named("idToCaminhao")
    default Caminhao idToCaminhao(Long caminhaoId) {
        if (caminhaoId == null) return null;
        Caminhao caminhao = new Caminhao();
        caminhao.setId(caminhaoId);
        return caminhao;
    }
    
    @Named("idToMotorista")
    default Motorista idToMotorista(Long motoristaId) {
        if (motoristaId == null) return null;
        Motorista motorista = new Motorista();
        motorista.setId(motoristaId);
        return motorista;
    }
    
    @Named("idToSolicitacao")
    default Solicitacao idToEntrega(Long solicitacaoId) {
        if (solicitacaoId == null) return null;
        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setId(solicitacaoId);
        return solicitacao;
    }
}