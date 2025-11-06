package com.example.frota.caminhao;

import com.example.frota.marca.Marca;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-06T03:55:32-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 24.0.2 (Oracle Corporation)"
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
        Integer ano = null;
        String placa = null;
        Double altura = null;
        Double largura = null;
        Double comprimento = null;
        Double cargaMaxima = null;

        marcaId = caminhaoMarcaId( caminhao );
        id = caminhao.getId();
        modelo = caminhao.getModelo();
        ano = caminhao.getAno();
        placa = caminhao.getPlaca();
        altura = caminhao.getAltura();
        largura = caminhao.getLargura();
        comprimento = caminhao.getComprimento();
        cargaMaxima = caminhao.getCargaMaxima();

        AtualizacaoCaminhao atualizacaoCaminhao = new AtualizacaoCaminhao( id, modelo, ano, placa, altura, largura, comprimento, cargaMaxima, marcaId );

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
        if ( dto.ano() != null ) {
            caminhao.setAno( dto.ano() );
        }
        caminhao.setPlaca( dto.placa() );
        if ( dto.altura() != null ) {
            caminhao.setAltura( dto.altura() );
        }
        if ( dto.largura() != null ) {
            caminhao.setLargura( dto.largura() );
        }
        if ( dto.comprimento() != null ) {
            caminhao.setComprimento( dto.comprimento() );
        }
        if ( dto.cargaMaxima() != null ) {
            caminhao.setCargaMaxima( dto.cargaMaxima() );
        }

        return caminhao;
    }

    @Override
    public void updateEntityFromDto(AtualizacaoCaminhao dto, Caminhao caminhao) {
        if ( dto == null ) {
            return;
        }

        caminhao.setMarca( idToMarca( dto.marcaId() ) );
        caminhao.setModelo( dto.modelo() );
        if ( dto.ano() != null ) {
            caminhao.setAno( dto.ano() );
        }
        caminhao.setPlaca( dto.placa() );
        if ( dto.altura() != null ) {
            caminhao.setAltura( dto.altura() );
        }
        if ( dto.largura() != null ) {
            caminhao.setLargura( dto.largura() );
        }
        if ( dto.comprimento() != null ) {
            caminhao.setComprimento( dto.comprimento() );
        }
        if ( dto.cargaMaxima() != null ) {
            caminhao.setCargaMaxima( dto.cargaMaxima() );
        }
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
