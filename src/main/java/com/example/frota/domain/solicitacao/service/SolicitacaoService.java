package com.example.frota.domain.solicitacao.service;

import com.example.frota.application.dto.solicitacao.AtualizacaoSolicitacao;
import com.example.frota.domain.caixa.model.Caixa;
import com.example.frota.domain.caixa.service.CaixaService;
import com.example.frota.domain.caminhao.model.Caminhao;
import com.example.frota.domain.caminhao.service.CaminhaoService;
import com.example.frota.domain.motorista.model.Motorista;
import com.example.frota.domain.produto.model.Produto;
import com.example.frota.domain.produto.service.ProdutoService;
import com.example.frota.domain.solicitacao.model.Solicitacao;
import com.example.frota.domain.solicitacao.mapper.SolicitacaoMapper;
import com.example.frota.domain.solicitacao.repository.SolicitacaoRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SolicitacaoService {
    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    @Autowired
    private CaixaService caixaService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CaminhaoService caminhaoService;

    @Autowired
    private SolicitacaoMapper solicitacaoMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${here.rotas.api.url}")
    private String urlRotas;

    @Value("${here.api.key}")
    private String apiKey;

    @Value("${here.geocode.url}")
    private String urlGeocode;

    public Solicitacao salvarOuAtualizar(AtualizacaoSolicitacao dto) throws Exception {
        Caixa caixa = caixaService.procurarPorId(dto.caixaId())
                .orElseThrow(() -> new EntityNotFoundException("Caixa não encontrada com ID: " + dto.caixaId()));

        Produto produto = produtoService.procurarPorId(dto.produtoId())
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + dto.produtoId()));

        Caminhao caminhao = caminhaoService.procurarPorId(dto.caminhaoId())
                .orElseThrow(() -> new EntityNotFoundException("Caminhão não encontrado com ID: " + dto.caminhaoId()));

        if (dto.id() != null) {
            Solicitacao existente = solicitacaoRepository.findById(dto.id())
                    .orElseThrow(() -> new EntityNotFoundException("Solicitação não encontrada com ID: " + dto.id()));
            solicitacaoMapper.updateEntityFromDto(dto, existente);
            return criarSolicitacao(existente, caixa, produto, caminhao);
        } else {
            Solicitacao novaSolicitacao = solicitacaoMapper.toEntityFromAtualizacao(dto);
            return criarSolicitacao(novaSolicitacao, caixa, produto, caminhao);
        }
    }
    
	public Optional<Solicitacao> buscar(long id) {
		return solicitacaoRepository.findById(id);
	}

    private Solicitacao criarSolicitacao(Solicitacao solicitacao, Caixa caixa, Produto produto, Caminhao caminhao) throws Exception {
        solicitacao.setCaixa(caixa);
        solicitacao.setProduto(produto);
        solicitacao.setCaminhao(caminhao);
        
        double pesoProduto = produto.getPeso();
        
        if(!verificaTamanhoCaixa(pesoProduto, caixa.getLimitePeso())) {
        	throw new Exception("A caixa não suporta o peso do Produto");
       	}
        
        double pesoCubado = calculaPesoCubado(solicitacao.getCaixa().getComprimento(), solicitacao.getCaixa().getLargura(), solicitacao.getCaixa().getAltura(), solicitacao.getCaminhao().getFatorCubagem());

        //Calcular frete para atualizar o preco
        //O calculo usa o peso considerado, esse deve ser o peso do produto
        Map<String, Object> freteInfo = calcularFrete(solicitacao.getCepOrigem(), solicitacao.getCepDestino(), comparaPesoCubadoPesoReal(pesoCubado, pesoProduto));

        //Dou cast de Object para Number para poder transformar em double
        double distancia = ((Number) freteInfo.get("distanciaKm")).intValue();
        solicitacao.setDistanciaKm(distancia);

        double custoTotalFrete = (double) Math.round(((Number) freteInfo.get("valorFreteTotal")).doubleValue());
        solicitacao.setCustoFreteCalculado(custoTotalFrete);

        double totalPedagios = ((Number) freteInfo.get("pedagiosTotal")).doubleValue();
        solicitacao.setCustoPedagios(totalPedagios);

        double pesoTotal = (double) Math.round(((Number) freteInfo.get("pesoKg")).doubleValue());
        solicitacao.setPesoConsideradoKg(pesoTotal);

        solicitacao.setDataSolicitacao(LocalDateTime.now());

        return solicitacaoRepository.save(solicitacao);
    }

    public List<Solicitacao> procurarTodos() {
        return solicitacaoRepository.findAll(Sort.by("dataSolicitacao").ascending());
    }

    public void apagarPorId(Long id) {
        solicitacaoRepository.deleteById(id);
    }

    public Optional<Solicitacao> procurarPorId(Long id) {
        return solicitacaoRepository.findById(id);
    }

    public String buscarCoordenadasCep(String cep) {
        URI uri = UriComponentsBuilder
                .fromUriString(urlGeocode)
                .queryParam("q", cep)
                .queryParam("apiKey", apiKey)
                .build()
                .toUri();

        try {
            String response = restTemplate.getForObject(uri, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response);
            JsonNode items = root.path("items");
            if (items.isArray() && !items.isEmpty()) {
                JsonNode position = items.get(0).path("position");
                double lat = position.path("lat").asDouble();
                double lng = position.path("lng").asDouble();
                return lat + "," + lng;
            }
            return "Coordenadas não encontradas";
        } catch (HttpClientErrorException e) {
            return "Erro ao buscar coordenadas: " + e.getStatusCode();
        } catch (Exception e) {
            return "Erro ao processar a resposta da API";
        }
    }

    public Map<String, Object> buscarRota(String origem, String destino) {
        URI uri = UriComponentsBuilder
                .fromUriString(urlRotas)
                .queryParam("transportMode", "truck")
                .queryParam("origin", origem)
                .queryParam("destination", destino)
                .queryParam("return", "summary,tolls")
                .queryParam("apiKey", apiKey)
                .build()
                .toUri();

        try {
            String response = restTemplate.getForObject(uri, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response);
            JsonNode routes = root.path("routes");

            if (routes.isArray() && !routes.isEmpty()) {
                JsonNode firstRoute = routes.get(0);
                JsonNode sections = firstRoute.path("sections");

                double totalPedagios = 0;
                int totalDuracao = 0;
                int totalDistancia = 0;
                int totalDuracaoBase = 0;

                if (sections.isArray()) {
                    for (JsonNode section : sections) {
                        JsonNode summary = section.path("summary");
                        totalDuracao += summary.path("duration").asInt();
                        totalDistancia += summary.path("length").asInt();
                        totalDuracaoBase += summary.path("baseDuration").asInt();

                        JsonNode tolls = section.path("tolls");
                        if (tolls.isArray()) {
                            for (JsonNode toll : tolls) {
                                JsonNode fares = toll.path("fares");
                                if (fares.isArray()) {
                                    for (JsonNode fare : fares) {
                                        totalPedagios += fare.path("price").path("value").asDouble();
                                    }
                                }
                            }
                        }
                    }
                }

                Map<String, Object> result = new HashMap<>();
                result.put("totalPedagios", totalPedagios);
                result.put("duracao", totalDuracao);
                result.put("distancia", totalDistancia);
                result.put("duracaoBase", totalDuracaoBase);

                return result;
            }

            Map<String, Object> erro = new HashMap<>();
            erro.put("erro", "Nenhuma rota encontrada.");
            return erro;

        } catch (HttpClientErrorException e) {
            Map<String, Object> erro = new HashMap<>();
            erro.put("error", "Erro ao buscar rota: " + e.getStatusCode());
            return erro;
        } catch (Exception e) {
            Map<String, Object> erro = new HashMap<>();
            erro.put("error", "Erro ao processar a resposta da API de rotas");
            return erro;
        }
    }

    public Map<String, Object> calcularFrete(String cepOrigem, String cepDestino, double pesoKg) throws Exception {
        String origemCoords = buscarCoordenadasCep(cepOrigem);
        String destinoCoords = buscarCoordenadasCep(cepDestino);

        Map<String, Object> rota = buscarRota(origemCoords, destinoCoords);

        if (rota.containsKey("error") || rota.containsKey("erro")) {
            throw new Exception("Erro ao calcular frete: " + rota.values()); // Retorna erro caso a API falhe
        }

        double distanciaMetros = ((Number) rota.get("distancia")).doubleValue();
        double distanciaKm = (distanciaMetros / 1000.0);
        double totalPedagios = ((Number) rota.get("totalPedagios")).doubleValue();

        double valorPorKm = 2.5; //2,50 reais por km rodado
        double valorPorKg = 0.20; // 0,20 reais por kg da caixa

        double custoDistancia = distanciaKm * valorPorKm;
        double custoPeso = pesoKg * valorPorKg;
        double valorTotal = custoDistancia + custoPeso + totalPedagios;

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("distanciaKm", distanciaKm);
        resultado.put("pesoKg", pesoKg);
        resultado.put("pedagiosTotal", totalPedagios);
        resultado.put("valorFreteTotal", valorTotal);

        return resultado;
    }
    
	public double calculaPesoCubado(double comprimento, double largura, double altura, double fatorCubagem) {
		double volume = comprimento * largura * altura;
		double pesoCubado = volume * fatorCubagem;
		return pesoCubado / 1000;
	}
	
	public double comparaPesoCubadoPesoReal(double pesoCubado, double pesoReal) {
		double maiorPeso;
		if (pesoCubado > pesoReal) {
			maiorPeso = pesoCubado;
		} else {
			maiorPeso = pesoReal;
		}
		return maiorPeso;
	}
	
	public boolean verificaTamanhoCaixa(double pesoProduto, double limitePesoCaixa) {
		boolean compativel;
		if (pesoProduto > limitePesoCaixa) {
			compativel = false;
		} else {
			compativel = true;
		}
		return compativel;
	}
}
