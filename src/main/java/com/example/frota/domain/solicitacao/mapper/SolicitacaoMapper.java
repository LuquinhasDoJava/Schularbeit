package com.example.frota.domain.solicitacao.mapper;

import com.example.frota.application.dto.solicitacao.AtualizacaoSolicitacao;
import com.example.frota.domain.solicitacao.model.Solicitacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.example.frota.domain.produto.model.Produto;
import com.example.frota.domain.caixa.model.Caixa;
import com.example.frota.domain.caminhao.model.Caminhao;

@Mapper(componentModel = "spring")
public interface SolicitacaoMapper {
    
    @Mapping(target = "caixaId", source = "caixa.id")
    @Mapping(target = "produtoId", source = "produto.id")
    @Mapping(target = "caminhaoId", source = "caminhao.id")
    AtualizacaoSolicitacao toAtualizacaoDto(Solicitacao solitacao);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "caixa", source = "caixaId", qualifiedByName = "idToCaixa")
    @Mapping(target = "produto", source = "produtoId", qualifiedByName = "idToProduto")
    @Mapping(target = "caminhao", source = "caminhaoId", qualifiedByName = "idToCaminhao")
    Solicitacao toEntityFromAtualizacao(AtualizacaoSolicitacao dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "caixa", source = "caixaId", qualifiedByName = "idToCaixa")
    @Mapping(target = "produto", source = "produtoId", qualifiedByName = "idToProduto")
    @Mapping(target = "caminhao", source = "caminhaoId", qualifiedByName = "idToCaminhao")
    void updateEntityFromDto(AtualizacaoSolicitacao dto, @MappingTarget Solicitacao solicitacao);
    
    @Named("idToCaixa")
    default Caixa idToCaixa(Long caixaId) {
        if (caixaId == null) return null;
        Caixa caixa = new Caixa();
        caixa.setId(caixaId);
        return caixa;
    }
    
    @Named("idToProduto")
    default Produto idToProduto(Long produtoId) {
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
