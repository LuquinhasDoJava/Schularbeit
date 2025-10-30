package com.example.frota;

import com.example.frota.entity.Caixa;
import com.example.frota.entity.Produto;
import com.example.frota.repository.CaixaRepository;
import com.example.frota.repository.EncomendaRepository;
import com.example.frota.service.CaixaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CaixaServiceIntegrationTest {

    @Autowired
    private CaixaService caixaService;

    @Autowired
    private CaixaRepository caixaRepository;

    @Autowired
    private EncomendaRepository encomendaRepository;

    @BeforeEach
    void setUp() {
        // Primeiro deleta as encomendas que referenciam as caixas
        encomendaRepository.deleteAll();
        // Depois pode deletar as caixas
        caixaRepository.deleteAll();
    }

    @Test
    void deveSalvarERecuperarCaixaDoBanco() {
        // Arrange
        Caixa caixa = new Caixa();
        caixa.setAltura(0.5);
        caixa.setLargura(0.4);
        caixa.setComprimento(0.6);
        caixa.setMaterial("Papelão");
        caixa.setLimitePeso(20.0);

        // Act
        Caixa salva = caixaService.salvar(caixa);
        Caixa encontrada = caixaRepository.findById(salva.getId()).orElse(null);

        // Assert
        assertThat(encontrada).isNotNull();
        assertThat(encontrada.getAltura()).isEqualTo(0.5);
        assertThat(encontrada.getMaterial()).isEqualTo("Papelão");
        assertThat(encontrada.getVolume()).isEqualTo(0.5 * 0.4 * 0.6);
    }

    @Test
    void deveDeletarCaixaDoBanco() {
        // Arrange
        Caixa caixa = new Caixa();
        caixa.setAltura(0.5);
        caixa.setLargura(0.4);
        caixa.setComprimento(0.6);
        caixa.setMaterial("Papelão");
        caixa.setLimitePeso(20.0);
        Caixa salva = caixaService.salvar(caixa);

        // Act
        caixaService.apagarPorId(salva.getId());

        // Assert
        assertThat(caixaRepository.findById(salva.getId())).isEmpty();
    }

    @Test
    void deveEncontrarCaixaMaisProximaParaProduto() {
        // Arrange - Cria caixas de diferentes tamanhos
        Caixa caixaPequena = criarCaixa(0.3, 0.4, 0.5, "Papelão", 10.0);
        Caixa caixaMedia = criarCaixa(0.6, 0.7, 0.8, "Madeira", 30.0);
        Caixa caixaGrande = criarCaixa(0.8, 0.9, 1.0, "Plástico", 50.0);

        // Arrange - Cria produto
        Produto produto = new Produto();
        produto.setPeso(5.0);
        produto.setAltura(0.4);
        produto.setLargura(0.3);
        produto.setComprimento(0.5);

        // Act
        Caixa caixaEscolhida = caixaService.escolherCaixaMaisProxima(produto);

        // Assert - Deve escolher a caixa pequena (mais próxima do tamanho do produto)
        assertThat(caixaEscolhida).isNotNull();
        assertThat(caixaEscolhida.getMaterial()).isEqualTo("Papelão");
        assertThat(caixaEscolhida.getLimitePeso()).isGreaterThanOrEqualTo(produto.getPeso());
    }

    @Test
    void deveRetornarNullQuandoNaoHaCaixaCompativel() {
        // Arrange - Cria apenas caixa pequena
        criarCaixa(0.3, 0.4, 0.5, "Papelão", 10.0);

        // Arrange - Cria produto grande
        Produto produtoGrande = new Produto();
        produtoGrande.setPeso(25.0);
        produtoGrande.setAltura(1.0);
        produtoGrande.setLargura(1.0);
        produtoGrande.setComprimento(1.0);

        // Act
        Caixa caixaEscolhida = caixaService.escolherCaixaMaisProxima(produtoGrande);

        // Assert
        assertThat(caixaEscolhida).isNull();
    }

    @Test
    void deveEscolherCaixaComMenorVolumeCompative() {
        // Arrange - Cria múltiplas caixas compatíveis
        Caixa caixaMedia1 = criarCaixa(0.6, 0.7, 0.8, "Madeira", 30.0);  // Volume: 0.336
        Caixa caixaMedia2 = criarCaixa(0.5, 0.6, 0.7, "Papelão", 25.0);  // Volume: 0.210 (menor)
        Caixa caixaGrande = criarCaixa(0.8, 0.9, 1.0, "Plástico", 50.0); // Volume: 0.720

        // Arrange - Cria produto
        Produto produto = new Produto();
        produto.setPeso(5.0);
        produto.setAltura(0.4);
        produto.setLargura(0.3);
        produto.setComprimento(0.5);

        // Act
        Caixa caixaEscolhida = caixaService.escolherCaixaMaisProxima(produto);

        // Assert - Deve escolher a caixa com menor volume compatível (caixaMedia2)
        assertThat(caixaEscolhida).isNotNull();
        assertThat(caixaEscolhida.getMaterial()).isEqualTo("Papelão");
        assertThat(caixaEscolhida.getVolume()).isEqualTo(0.5 * 0.6 * 0.7); // 0.210
    }

    @Test
    void deveEncontrarCaixaParaProdutoNoLimiteDePeso() {
        // Arrange
        Caixa caixaNoLimite = criarCaixa(0.5, 0.5, 0.5, "Papelão", 15.0);

        // Arrange - Produto com peso no limite
        Produto produto = new Produto();
        produto.setPeso(15.0); // Exatamente no limite
        produto.setAltura(0.4);
        produto.setLargura(0.4);
        produto.setComprimento(0.4);

        // Act
        Caixa caixaEscolhida = caixaService.escolherCaixaMaisProxima(produto);

        // Assert
        assertThat(caixaEscolhida).isNotNull();
        assertThat(caixaEscolhida.getLimitePeso()).isEqualTo(15.0);
    }

    @Test
    void naoDeveEncontrarCaixaParaProdutoAcimaDoLimiteDePeso() {
        // Arrange
        Caixa caixa = criarCaixa(0.5, 0.5, 0.5, "Papelão", 15.0);

        // Arrange - Produto acima do limite de peso
        Produto produto = new Produto();
        produto.setPeso(16.0); // Acima do limite
        produto.setAltura(0.4);
        produto.setLargura(0.4);
        produto.setComprimento(0.4);

        // Act
        Caixa caixaEscolhida = caixaService.escolherCaixaMaisProxima(produto);

        // Assert
        assertThat(caixaEscolhida).isNull();
    }

    private Caixa criarCaixa(double altura, double largura, double comprimento,
                             String material, double limitePeso) {
        Caixa caixa = new Caixa();
        caixa.setAltura(altura);
        caixa.setLargura(largura);
        caixa.setComprimento(comprimento);
        caixa.setMaterial(material);
        caixa.setLimitePeso(limitePeso);
        return caixaRepository.save(caixa);
    }
}