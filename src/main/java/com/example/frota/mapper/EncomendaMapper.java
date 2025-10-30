package com.example.frota.mapper;

import com.example.frota.dto.CadastroEncomenda;
import com.example.frota.entity.Encomenda;
import com.example.frota.entity.Caixa;
import com.example.frota.entity.Caminhao;
import com.example.frota.entity.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface EncomendaMapper {

    // Converte Entity para DTO (para visualização/edição)
    @Mapping(target = "caixaId", source = "caixa.id")
    @Mapping(target = "caminhaoId", source = "caminhao.id")
    @Mapping(target = "produtoId", source = "produto.id")
    @Mapping(target = "pesoReal", source = "pesoReal")
    CadastroEncomenda toCadastroDto(Encomenda encomenda);

    // Converte DTO para Entity (para criação NOVA - ignora ID)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "caixa", source = "caixaId", qualifiedByName = "idToCaixa")
    @Mapping(target = "caminhao", source = "caminhaoId", qualifiedByName = "idToCaminhao")
    @Mapping(target = "produto", source = "produtoId", qualifiedByName = "idToProduto")
    @Mapping(target = "pesoReal", source = "pesoReal")
    @Mapping(target = "distanciaKm", source = "distanciaKm")
    Encomenda toEntityFromCadastro(CadastroEncomenda dto);

    // Atualiza Entity existente com dados do DTO
    @Mapping(target = "id", ignore = true) // Não atualiza ID
    @Mapping(target = "caixa", source = "caixaId", qualifiedByName = "idToCaixa")
    @Mapping(target = "caminhao", source = "caminhaoId", qualifiedByName = "idToCaminhao")
    @Mapping(target = "produto", source = "produtoId", qualifiedByName = "idToProduto")
    @Mapping(target = "pesoReal", source = "pesoReal")
    @Mapping(target = "distanciaKm", source = "distanciaKm")
    void updateEntityFromDto(CadastroEncomenda dto, @MappingTarget Encomenda encomenda);

    // Métodos para converter IDs em entidades
    @Named("idToCaixa")
    default Caixa idToCaixa(Long caixaId) {
        if (caixaId == null) return null;
        Caixa caixa = new Caixa();
        caixa.setId(caixaId);
        return caixa;
    }

    @Named("idToCaminhao")
    default Caminhao idToCaminhao(Long caminhaoId) {
        if (caminhaoId == null) return null;
        Caminhao caminhao = new Caminhao();
        caminhao.setId(caminhaoId);
        return caminhao;
    }

    @Named("idToProduto")
    default Produto idToProduto(Long produtoId) {
        if (produtoId == null) return null;
        Produto produto = new Produto();
        produto.setId(produtoId);
        return produto;
    }
}