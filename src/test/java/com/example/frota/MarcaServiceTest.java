package com.example.frota;

import com.example.frota.dto.AtualizacaoMarca;
import com.example.frota.dto.CadastroMarca;
import com.example.frota.entity.Marca;
import com.example.frota.repository.MarcaRepository;
import com.example.frota.service.MarcaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MarcaServiceTest {

    @Autowired
    private MarcaService marcaService;

    @Autowired
    private MarcaRepository marcaRepository;

    @BeforeEach
    void setUp() {
        // Limpa o banco antes de cada teste
        marcaRepository.deleteAll();
    }

    @Test
    void deveCriarNovaMarcaComSucesso() {
        // Arrange
        CadastroMarca dados = new CadastroMarca("Toyota", "Japão");

        // Act
        Marca marcaSalva = marcaService.criar(dados);

        // Assert
        assertThat(marcaSalva.getId()).isNotNull();
        assertThat(marcaSalva.getNome()).isEqualTo("Toyota");
        assertThat(marcaSalva.getPais()).isEqualTo("Japão");
    }

    @Test
    void deveSalvarMarcaDiretamenteComSucesso() {
        // Arrange
        Marca marca = new Marca();
        marca.setNome("Ford");
        marca.setPais("EUA");

        // Act
        Marca marcaSalva = marcaService.salvar(marca);

        // Assert
        assertThat(marcaSalva.getId()).isNotNull();
        assertThat(marcaSalva.getNome()).isEqualTo("Ford");
    }

    @Test
    void deveListarTodasMarcasOrdenadasPorNome() {
        // Arrange
        marcaService.criar(new CadastroMarca("BMW", "Alemanha"));
        marcaService.criar(new CadastroMarca("Audi", "Alemanha"));
        marcaService.criar(new CadastroMarca("Chevrolet", "EUA"));

        // Act
        List<Marca> marcas = marcaService.procurarTodos();

        // Assert
        assertThat(marcas).hasSize(3);
        assertThat(marcas.get(0).getNome()).isEqualTo("Audi"); // Primeiro por ordem alfabética
        assertThat(marcas.get(1).getNome()).isEqualTo("BMW");
        assertThat(marcas.get(2).getNome()).isEqualTo("Chevrolet");
    }

    @Test
    void deveEncontrarMarcaPorId() {
        // Arrange
        Marca marcaSalva = marcaService.criar(new CadastroMarca("Honda", "Japão"));
        Long id = marcaSalva.getId();

        // Act
        Optional<Marca> marcaEncontrada = marcaService.procurarPorId(id);

        // Assert
        assertThat(marcaEncontrada).isPresent();
        assertThat(marcaEncontrada.get().getNome()).isEqualTo("Honda");
    }

    @Test
    void deveRetornarVazioQuandoMarcaNaoExiste() {
        // Act
        Optional<Marca> marcaEncontrada = marcaService.procurarPorId(999L);

        // Assert
        assertThat(marcaEncontrada).isEmpty();
    }

    @Test
    void deveAtualizarMarcaComSucesso() {
        // Arrange
        Marca marcaSalva = marcaService.criar(new CadastroMarca("Nissan", "Japão"));
        Long id = marcaSalva.getId();

        AtualizacaoMarca dadosAtualizacao = new AtualizacaoMarca(
                id, "Nissan Motors", "Japan"
        );

        // Act
        marcaService.atualizarMarca(dadosAtualizacao);

        // Assert
        Optional<Marca> marcaAtualizada = marcaService.procurarPorId(id);
        assertThat(marcaAtualizada).isPresent();
        assertThat(marcaAtualizada.get().getNome()).isEqualTo("Nissan Motors");
        assertThat(marcaAtualizada.get().getPais()).isEqualTo("Japan");
    }

    @Test
    void deveAtualizarApenasNomeQuandoPaisENull() {
        // Arrange
        Marca marcaSalva = marcaService.criar(new CadastroMarca("Hyundai", "Coreia do Sul"));
        Long id = marcaSalva.getId();

        AtualizacaoMarca dadosAtualizacao = new AtualizacaoMarca(
                id, "Hyundai Motors", null
        );

        // Act
        marcaService.atualizarMarca(dadosAtualizacao);

        // Assert
        Optional<Marca> marcaAtualizada = marcaService.procurarPorId(id);
        assertThat(marcaAtualizada).isPresent();
        assertThat(marcaAtualizada.get().getNome()).isEqualTo("Hyundai Motors");
        assertThat(marcaAtualizada.get().getPais()).isEqualTo("Coreia do Sul"); // Manteve o original
    }

    @Test
    void deveDeletarMarcaComSucesso() {
        // Arrange
        Marca marcaSalva = marcaService.criar(new CadastroMarca("Kia", "Coreia do Sul"));
        Long id = marcaSalva.getId();

        // Act
        marcaService.apagarPorId(id);

        // Assert
        Optional<Marca> marcaDeletada = marcaService.procurarPorId(id);
        assertThat(marcaDeletada).isEmpty();
    }

    @Test
    void naoDeveLancarExcecaoAoDeletarMarcaInexistente() {
        // Act & Assert - Não deve lançar exceção
        assertDoesNotThrow(() -> marcaService.apagarPorId(999L));
    }

    @Test
    void deveLancarExcecaoAoTentarCriarMarcaComNomeDuplicado() {
        // Arrange
        marcaService.criar(new CadastroMarca("Volvo", "Suécia"));

        // Act & Assert - Deve lançar exceção ao tentar criar marca com nome duplicado
        Marca marcaDuplicada = new Marca();
        marcaDuplicada.setNome("Volvo");
        marcaDuplicada.setPais("China");

        // Deve lançar DataIntegrityViolationException devido à constraint UNIQUE
        assertThrows(org.springframework.dao.DataIntegrityViolationException.class,
                () -> marcaService.salvar(marcaDuplicada));
    }

    @Test
    void salvarMarca(){
        Marca marca = new Marca("Ford","EUA");

        marcaService.salvar(marca);
    }
}