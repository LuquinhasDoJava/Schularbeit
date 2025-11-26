package com.example.frota.encomenda;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.example.frota.produto.Produto;
import com.example.frota.caminhao.Caminhao;

@Mapper(componentModel = "spring")
public interface EncomendaMapper {

    @Mapping(target = "produtoId", source = "produto.id")
    @Mapping(target = "caminhaoId", source = "caminhao.id")
    @Mapping(target = "horarioColeta", source = "horarioColeta")
    @Mapping(target = "statusEntrega", source = "statusEntrega")
    AtualizacaoEncomenda toAtualizacaoDto(Encomenda encomenda);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produto", source = "produtoId", qualifiedByName = "idToProduto")
    @Mapping(target = "caminhao", source = "caminhaoId", qualifiedByName = "idToCaminhao")
    @Mapping(target = "statusEntrega", constant = "SOLICITADA")
    @Mapping(target = "dataSolicitacao", expression = "java(java.time.LocalDateTime.now())")
    Encomenda toEntityFromAtualizacao(AtualizacaoEncomenda dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produto", source = "produtoId", qualifiedByName = "idToProduto")
    @Mapping(target = "caminhao", source = "caminhaoId", qualifiedByName = "idToCaminhao")
    @Mapping(target = "dataSolicitacao", ignore = true)
    void updateEntityFromDto(AtualizacaoEncomenda dto, @MappingTarget Encomenda encomenda);

    @Named("idToProduto")
    default Produto idToProduto(Integer produtoId) {
        if (produtoId == null) return null;
        Produto produto = new Produto();
        produto.setId(produtoId);
        return produto;
    }

    @Named("idToCaminhao")
    default Caminhao idToCaminhao(Long caminhaoId) {
        if (caminhaoId == null) return null;
        Caminhao caminhao = new Caminhao();
        caminhao.setId(caminhaoId);
        return caminhao;
    }
}