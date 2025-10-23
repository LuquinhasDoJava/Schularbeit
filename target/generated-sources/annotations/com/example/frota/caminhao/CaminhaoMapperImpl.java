package com.example.frota.caminhao;

import com.example.frota.marca.Marca;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-23T17:24:14-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
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
        caminhao.setModelo( dto.modelo() );
        caminhao.setPlaca( dto.placa() );
        if ( dto.cargaMaxima() != null ) {
            caminhao.setCargaMaxima( dto.cargaMaxima() );
        }
        caminhao.setAno( dto.ano() );

        return caminhao;
    }

    @Override
    public void updateEntityFromDto(AtualizacaoCaminhao dto, Caminhao caminhao) {
        if ( dto == null ) {
            return;
        }

        caminhao.setMarca( idToMarca( dto.marcaId() ) );
        caminhao.setModelo( dto.modelo() );
        caminhao.setPlaca( dto.placa() );
        if ( dto.cargaMaxima() != null ) {
            caminhao.setCargaMaxima( dto.cargaMaxima() );
        }
        caminhao.setAno( dto.ano() );
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
