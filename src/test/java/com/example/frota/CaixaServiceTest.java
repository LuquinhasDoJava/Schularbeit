package com.example.frota;

import com.example.frota.entity.Caixa;
import com.example.frota.entity.Produto;
import com.example.frota.repository.CaixaRepository;
import com.example.frota.service.CaixaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CaixaServiceTest {

    @Mock
    private CaixaRepository caixaRepository;

    @InjectMocks
    private CaixaService caixaService;

    private Caixa caixa;
    private Produto produto;

    @BeforeEach
    void setUp() {
        // Configura uma caixa de exemplo
        caixa = new Caixa();
        caixa.setId(1L);
        caixa.setAltura(0.5);
        caixa.setLargura(0.4);
        caixa.setComprimento(0.6);
        caixa.setMaterial("Papelão");
        caixa.setLimitePeso(20.0);

        // Configura um produto de exemplo
        produto = new Produto();
        produto.setId(1L);
        produto.setPeso(5.0);
        produto.setAltura(0.4);
        produto.setLargura(0.3);
        produto.setComprimento(0.5);
    }

    @Test
    void deveSalvarCaixaComSucesso() {
        // Arrange
        when(caixaRepository.save(any(Caixa.class))).thenReturn(caixa);

        // Act
        Caixa resultado = caixaService.salvar(caixa);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getAltura()).isEqualTo(0.5);
        assertThat(resultado.getMaterial()).isEqualTo("Papelão");

        verify(caixaRepository).save(caixa);
    }

    @Test
    void deveApagarCaixaPorId() {
        // Arrange
        doNothing().when(caixaRepository).deleteById(1L);

        // Act
        caixaService.apagarPorId(1L);

        // Assert
        verify(caixaRepository).deleteById(1L);
    }

    @Test
    void deveEscolherCaixaMaisProximaParaProduto() {
        // Arrange
        when(caixaRepository.findCaixaMaisProxima(
                produto.getComprimento(),
                produto.getLargura(),
                produto.getAltura(),
                produto.getPeso()
        )).thenReturn(caixa);

        // Act
        Caixa resultado = caixaService.escolherCaixaMaisProxima(produto);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getAltura()).isGreaterThanOrEqualTo(produto.getAltura());
        assertThat(resultado.getLargura()).isGreaterThanOrEqualTo(produto.getLargura());
        assertThat(resultado.getComprimento()).isGreaterThanOrEqualTo(produto.getComprimento());
        assertThat(resultado.getLimitePeso()).isGreaterThanOrEqualTo(produto.getPeso());

        verify(caixaRepository).findCaixaMaisProxima(0.5, 0.3, 0.4, 5.0);
    }

    @Test
    void deveRetornarNullQuandoNaoEncontrarCaixaCompativel() {
        // Arrange
        when(caixaRepository.findCaixaMaisProxima(anyDouble(), anyDouble(), anyDouble(), anyDouble()))
                .thenReturn(null);

        // Act
        Caixa resultado = caixaService.escolherCaixaMaisProxima(produto);

        // Assert
        assertThat(resultado).isNull();
        verify(caixaRepository).findCaixaMaisProxima(0.5, 0.3, 0.4, 5.0);
    }

    @Test
    void deveCalcularVolumeDaCaixaCorretamente() {
        // Arrange
        Caixa caixaComVolume = new Caixa();
        caixaComVolume.setAltura(2.0);
        caixaComVolume.setLargura(3.0);
        caixaComVolume.setComprimento(4.0);

        // Act
        double volume = caixaComVolume.getVolume();

        // Assert
        assertThat(volume).isEqualTo(2.0 * 3.0 * 4.0); // 24.0
    }

    @Test
    void deveEscolherCaixaParaProdutoPesado() {
        // Arrange
        Produto produtoPesado = new Produto();
        produtoPesado.setPeso(45.0);
        produtoPesado.setAltura(0.7);
        produtoPesado.setLargura(0.8);
        produtoPesado.setComprimento(0.9);

        Caixa caixaGrande = new Caixa();
        caixaGrande.setId(2L);
        caixaGrande.setAltura(0.8);
        caixaGrande.setLargura(0.9);
        caixaGrande.setComprimento(1.0);
        caixaGrande.setMaterial("Plástico");
        caixaGrande.setLimitePeso(50.0);

        when(caixaRepository.findCaixaMaisProxima(0.9, 0.8, 0.7, 45.0))
                .thenReturn(caixaGrande);

        // Act
        Caixa resultado = caixaService.escolherCaixaMaisProxima(produtoPesado);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(2L);
        assertThat(resultado.getLimitePeso()).isGreaterThanOrEqualTo(produtoPesado.getPeso());
        assertThat(resultado.getMaterial()).isEqualTo("Plástico");
    }

    @Test
    void deveEscolherCaixaParaProdutoComDimensoesExatas() {
        // Arrange
        Produto produtoExato = new Produto();
        produtoExato.setPeso(10.0);
        produtoExato.setAltura(0.3);
        produtoExato.setLargura(0.4);
        produtoExato.setComprimento(0.5);

        Caixa caixaExata = new Caixa();
        caixaExata.setId(3L);
        caixaExata.setAltura(0.3);
        caixaExata.setLargura(0.4);
        caixaExata.setComprimento(0.5);
        caixaExata.setMaterial("Papelão");
        caixaExata.setLimitePeso(15.0);

        when(caixaRepository.findCaixaMaisProxima(0.5, 0.4, 0.3, 10.0))
                .thenReturn(caixaExata);

        // Act
        Caixa resultado = caixaService.escolherCaixaMaisProxima(produtoExato);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getAltura()).isEqualTo(produtoExato.getAltura());
        assertThat(resultado.getLargura()).isEqualTo(produtoExato.getLargura());
        assertThat(resultado.getComprimento()).isEqualTo(produtoExato.getComprimento());
    }

    @Test
    void deveLancarExcecaoQuandoProdutoForNulo() {
        // Act & Assert
        assertThrows(NullPointerException.class,
                () -> caixaService.escolherCaixaMaisProxima(null));
    }

    @Test
    void deveVerificarSeCaixaSuportaPesoDoProduto() {
        // Arrange
        Produto produtoNoLimite = new Produto();
        produtoNoLimite.setPeso(19.0); // Quase no limite da caixa (20.0)

        when(caixaRepository.findCaixaMaisProxima(anyDouble(), anyDouble(), anyDouble(), anyDouble()))
                .thenReturn(caixa);

        // Act
        Caixa resultado = caixaService.escolherCaixaMaisProxima(produtoNoLimite);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getLimitePeso()).isGreaterThanOrEqualTo(produtoNoLimite.getPeso());
    }
}