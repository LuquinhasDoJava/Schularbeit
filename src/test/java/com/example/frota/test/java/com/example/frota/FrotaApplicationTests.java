package com.example.frota;

import com.example.frota.service.MarcaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class FrotaApplicationTests {

    @Autowired
    private MarcaService marcaService;

    @Test
    void contextLoads() {
        // Teste básico para verificar se o contexto Spring carrega
        assertThat(marcaService).isNotNull();
    }

    @Test
    void deveCarregarContextoEJpaFuncionando() {
        // Teste simples para verificar se JPA está funcionando
        var marcas = marcaService.procurarTodos();
        assertThat(marcas).isNotNull(); // Pode estar vazio, mas não nulo
    }
}