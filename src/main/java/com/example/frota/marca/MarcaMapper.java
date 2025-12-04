package com.example.frota.marca;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MarcaMapper {

    // Converte Entity para DTO (para preencher formulário de edição)
    AtualizacaoMarca toAtualizacaoDto(Marca marca);

    // Converte DTO para Entity (para criação NOVA - ignora ID)
    @Mapping(target = "id", ignore = true)
    Marca toEntityFromAtualizacao(AtualizacaoMarca dto);

    // Atualiza Entity existente com dados do DTO
    @Mapping(target = "id", ignore = true) // Não atualiza ID
    void updateEntityFromDto(AtualizacaoMarca dto, @MappingTarget Marca marca);
}