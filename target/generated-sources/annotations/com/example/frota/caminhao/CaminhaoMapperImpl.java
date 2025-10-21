package com.example.frota.caminhao;

import com.example.frota.marca.Marca;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-03T22:21:35-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.v20250213-1140, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class CaminhaoMapperImpl implements CaminhaoMapper {

    @Override
    public AtualizacaoCaminhao toAtualizacaoDto(Caminhao caminhao) {
        if ( caminhao == null ) {
            return null;
        }

        Long marcaId = null;
        Long id = null;
        String modelo = null;
        String placa = null;
        Integer ano = null;
        Double cargaMaxima = null;

        marcaId = caminhaoMarcaId( caminhao );
        id = caminhao.getId();
        modelo = caminhao.getModelo();
        placa = caminhao.getPlaca();
        ano = caminhao.getAno();
        cargaMaxima = caminhao.getCargaMaxima();

        AtualizacaoCaminhao atualizacaoCaminhao = new AtualizacaoCaminhao( id, modelo, placa, ano, cargaMaxima, marcaId );

        return atualizacaoCaminhao;
    }

    @Override
    public Caminhao toEntityFromAtualizacao(AtualizacaoCaminhao dto) {
        if ( dto == null ) {
            return null;
        }

        Caminhao caminhao = new Caminhao();

        caminhao.setMarca( idToMarca( dto.marcaId() ) );
        if ( dto.ano() != null ) {
            caminhao.setAno( dto.ano() );
        }
        if ( dto.cargaMaxima() != null ) {
            caminhao.setCargaMaxima( dto.cargaMaxima() );
        }
        caminhao.setModelo( dto.modelo() );
        caminhao.setPlaca( dto.placa() );

        return caminhao;
    }

    @Override
    public void updateEntityFromDto(AtualizacaoCaminhao dto, Caminhao caminhao) {
        if ( dto == null ) {
            return;
        }

        caminhao.setMarca( idToMarca( dto.marcaId() ) );
        if ( dto.ano() != null ) {
            caminhao.setAno( dto.ano() );
        }
        if ( dto.cargaMaxima() != null ) {
            caminhao.setCargaMaxima( dto.cargaMaxima() );
        }
        caminhao.setModelo( dto.modelo() );
        caminhao.setPlaca( dto.placa() );
    }

    private Long caminhaoMarcaId(Caminhao caminhao) {
        if ( caminhao == null ) {
            return null;
        }
        Marca marca = caminhao.getMarca();
        if ( marca == null ) {
            return null;
        }
        long id = marca.getId();
        return id;
    }
}
