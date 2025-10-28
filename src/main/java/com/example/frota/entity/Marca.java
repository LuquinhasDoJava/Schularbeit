package com.example.frota.entity;

import com.example.frota.dto.AtualizacaoMarca;
import com.example.frota.dto.CadastroMarca;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "marca")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ="id")
public class Marca {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "marca_id")
	private long id;

	@Column(nullable = false, unique = true)
    private String nome;

	@Column(nullable = false, length = 20)
    private String pais;

    public void atualizarInformacoes(@Valid AtualizacaoMarca dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
            this.pais = dados.pais();
        }
    }

    public Marca(CadastroMarca dados){
        this.id = dados.id();
        this.pais = dados.pais();
        this.nome = dados.nome();
    }
}
