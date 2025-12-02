package com.example.frota.domain.manutencao.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.example.frota.application.dto.manutencao.AtualizacaoManutencao;
import com.example.frota.domain.caminhao.model.Caminhao;
import com.example.frota.domain.manutencao.model.Manutencao;


@Mapper(componentModel = "spring")
public interface ManutencaoMapper {
    
    // Converte Entity para DTO (para preencher formulário de edição)
    @Mapping(target = "caminhaoId", source = "caminhao.id")
    AtualizacaoManutencao toAtualizacaoDto(Manutencao caminhao);
    
    // Converte DTO para Entity (para criação NOVA - ignora ID)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "caminhao", source = "caminhaoId", qualifiedByName = "idToCaminhao")
    Manutencao toEntityFromAtualizacao(AtualizacaoManutencao dto);
    
    // Atualiza Entity existente com dados do DTO
    @Mapping(target = "id", ignore = true) // Não atualiza ID
    @Mapping(target = "caminhao", source = "caminhaoId", qualifiedByName = "idToCaminhao")
    void updateEntityFromDto(AtualizacaoManutencao dto, @MappingTarget Manutencao existente);
    
    // Método para converter marcaId em objeto Marca
    @Named("idToCaminhao")
    default Caminhao idToCaminhao(Long caminhaoId) {
        if (caminhaoId == null) return null;
        Caminhao caminhao = new Caminhao();
        caminhao.setId(caminhaoId);
        return caminhao;
    }
}