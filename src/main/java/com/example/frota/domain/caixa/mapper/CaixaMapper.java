package com.example.frota.domain.caixa.mapper;

import com.example.frota.application.dto.caixa.AtualizacaoCaixa;
import com.example.frota.domain.caixa.model.Caixa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CaixaMapper {

    AtualizacaoCaixa toAtualizacaoDto(Caixa caixa);

    @Mapping(target = "id", ignore = true)
    Caixa toEntityFromAtualizacao(AtualizacaoCaixa dto);

    void updateEntityFromDto(AtualizacaoCaixa dto, @MappingTarget Caixa caixa);

}
