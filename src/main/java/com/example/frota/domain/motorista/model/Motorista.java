package com.example.frota.domain.motorista.model;

import com.example.frota.application.dto.caixa.AtualizacaoCaixa;
import com.example.frota.application.dto.caixa.CadastroCaixa;
import com.example.frota.application.dto.motorista.AtualizacaoMotorista;
import com.example.frota.application.dto.motorista.CadastroMotorista;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "motorista")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Motorista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cnh;
    private String telefone;

    public Motorista(CadastroMotorista dados) {
        this.nome = dados.nome();
        this.cnh = dados.cnh();
        this.telefone = dados.telefone();
    }
    
	public Motorista(AtualizacaoMotorista dados) {
        this.nome = dados.nome();
        this.cnh = dados.cnh();
        this.telefone = dados.telefone();
	}
    
	public void atualizarInformacoes(AtualizacaoMotorista dados) {
		if(dados.nome() != "")
			this.nome = dados.nome();
		if(dados.cnh() != "")
			this.cnh = dados.cnh();
		if(dados.telefone() != "")
			this.telefone = dados.telefone();
	}
}