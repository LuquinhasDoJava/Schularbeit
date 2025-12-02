package com.example.frota.application.dto.solicitacao;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

public record CadastroSolicitacao(
		@NotBlank
		Long id,
		Long idProduto,
		Long idCaixa,
		Long idCaminhao,
		String cepOrigem,
		String cepDestino,
		LocalDateTime dataSolicitacao,
		Double distanciaKm,
		Double custoPedagios,
		Double pesoConsideradoKg,
		Double custoFreteCalculado
		) {
}

/*    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "produto_id", referencedColumnName = "id")
private Produto produto;

@ManyToOne
@JoinColumn(name = "caixa_id", referencedColumnName = "id")
private Caixa caixaEscolhida;

private String cepOrigem;
private String cepDestino;
private LocalDateTime dataSolicitacao;

private Double distanciaKm;
private Double custoPedagios;
private Double pesoConsideradoKg;
private Double custoFreteCalculado;

@Enumerated(EnumType.STRING)
private StatusSolicitacao status;
* 
* */