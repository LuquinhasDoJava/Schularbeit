package com.example.frota.produto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.example.frota.caixa.Caixa;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    // Converte Entity para DTO (para preencher formulário de edição)
    @Mapping(target = "caixaId", source = "caixa.id")
    AtualizacaoProduto toAtualizacaoDto(Produto produto);

    // Converte DTO para Entity (para criação NOVA - ignora ID)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "caixa", source = "caixaId", qualifiedByName = "idToCaixa")
    Produto toEntityFromAtualizacao(AtualizacaoProduto dto);

    // Atualiza Entity existente com dados do DTO
    @Mapping(target = "id", ignore = true) // Não atualiza ID
    @Mapping(target = "caixa", source = "caixaId", qualifiedByName = "idToCaixa")
    void updateEntityFromDto(AtualizacaoProduto dto, @MappingTarget Produto produto);

    // Método para converter caixaId em objeto Caixa
    @Named("idToCaixa")
    default Caixa idToCaixa(Long caixaId) {
        if (caixaId == null) return null;
        Caixa caixa = new Caixa();
        caixa.setId(caixaId);
        return caixa;
    }
}