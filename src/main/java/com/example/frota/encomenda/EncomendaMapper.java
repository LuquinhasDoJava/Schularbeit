package com.example.frota.encomenda;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.example.frota.produto.Produto;

@Mapper(componentModel = "spring")
public interface EncomendaMapper {

    // Converte Entity para DTO (para preencher formulário de edição)
    @Mapping(target = "produtoId", source = "produto.id")
    AtualizacaoEncomenda toAtualizacaoDto(Encomenda encomenda);

    // Converte DTO para Entity (para criação NOVA - ignora ID)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produto", source = "produtoId", qualifiedByName = "idToProduto")
    Encomenda toEntityFromAtualizacao(AtualizacaoEncomenda dto);

    // Atualiza Entity existente com dados do DTO
    @Mapping(target = "id", ignore = true) // Não atualiza ID
    @Mapping(target = "produto", source = "produtoId", qualifiedByName = "idToProduto")
    void updateEntityFromDto(AtualizacaoEncomenda dto, @MappingTarget Encomenda encomenda);

    // Metodo para converter produtoId em objeto Produto
    @Named("idToProduto")
    default Produto idToProduto(Integer produtoId) {
        if (produtoId == null) return null;
        Produto produto = new Produto();
        produto.setId(produtoId);
        return produto;
    }
}