package com.example.frota.entity;

import com.example.frota.dto.AtualizacaoMarca;
import com.example.frota.dto.CadastroMarca;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

@Entity
@Table(name = "marca")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "marca_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false, length = 20)
    private String pais;

    // ⚠️ CORRIJA este metodo - verifique nulls!
    public void atualizarInformacoes(@Valid AtualizacaoMarca dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.pais() != null) {
            this.pais = dados.pais();
        }
    }

    // ⚠️ MELHORE o construtor
    public Marca(CadastroMarca dados) {
        this.nome = dados.nome();
        this.pais = dados.pais();
    }

    // Adicione este construtor
    public Marca(String nome, String pais) {
        this.nome = nome;
        this.pais = pais;
    }
}