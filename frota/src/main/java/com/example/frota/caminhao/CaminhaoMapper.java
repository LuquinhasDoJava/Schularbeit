package com.example.frota.caminhao;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import com.example.frota.marca.Marca;

@Mapper(componentModel = "spring")
public interface CaminhaoMapper {

    @Mapping(target = "marcaId", source = "marca.id")
    AtualizacaoCaminhao toAtualizacaoDto(Caminhao caminhao);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "marca", source = "marcaId", qualifiedByName = "idToMarca")
    @Mapping(target = "statusManutencao", constant = "EM_DIA")
    @Mapping(target = "dataProximaManutencao", expression = "java(java.time.LocalDate.now().plusMonths(6))")
    Caminhao toEntityFromAtualizacao(AtualizacaoCaminhao dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "marca", source = "marcaId", qualifiedByName = "idToMarca")
    @Mapping(target = "statusManutencao", ignore = true)
    @Mapping(target = "dataProximaManutencao", ignore = true)
    void updateEntityFromDto(AtualizacaoCaminhao dto, @MappingTarget Caminhao caminhao);

    @Named("idToMarca")
    default Marca idToMarca(Long marcaId) {
        if (marcaId == null) return null;
        Marca marca = new Marca();
        marca.setId(marcaId);
        return marca;
    }
}