package com.example.frota.encomenda;

import com.example.frota.caixa.Caixa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.frota.caixa.CaixaService;
import com.example.frota.produto.Produto;
import com.example.frota.produto.ProdutoService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/encomenda")
public class EncomendaController {

    @Autowired
    private EncomendaService encomendaService;

    @Autowired
    private EncomendaMapper encomendaMapper;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CaixaService caixaService;

    @GetMapping
    public String carregaPaginaListagem(Model model) {
        model.addAttribute("listaEncomendas", encomendaService.procurarTodos());
        return "encomenda/listagem";
    }

    @GetMapping("/formulario")
    public String mostrarFormulario(@RequestParam(required = false) Integer id, Model model) {
        AtualizacaoEncomenda dto;
        if (id != null) {
            // edição: Carrega dados existentes
            Encomenda encomenda = encomendaService.procurarPorId(id)
                    .orElseThrow(() -> new EntityNotFoundException("Encomenda não encontrada"));
            dto = encomendaMapper.toAtualizacaoDto(encomenda);
        } else {
            // criação: DTO vazio
            dto = new AtualizacaoEncomenda(null, null, "", null, null);
        }
        model.addAttribute("encomenda", dto); // CORREÇÃO: era "templates/encomenda"
        model.addAttribute("produtos", produtoService.procurarTodos());
        return "encomenda/formulario";
    }

    @GetMapping("/formulario/{id}")
    public String carregaPaginaFormulario(@PathVariable("id") Integer id, Model model,
                                          RedirectAttributes redirectAttributes) {
        AtualizacaoEncomenda dto;
        try {
            if (id != null) {
                Encomenda encomenda = encomendaService.procurarPorId(id)
                        .orElseThrow(() -> new EntityNotFoundException("Encomenda não encontrada"));
                dto = encomendaMapper.toAtualizacaoDto(encomenda);
                model.addAttribute("encomenda", dto); // CORREÇÃO: era "templates/encomenda"
                model.addAttribute("produtos", produtoService.procurarTodos());
            }
            return "encomenda/formulario";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/encomenda";
        }
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("encomenda") @Valid AtualizacaoEncomenda dto, // CORREÇÃO: era "templates/encomenda"
                         BindingResult result,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("produtos", produtoService.procurarTodos());
            return "encomenda/formulario"; // CORREÇÃO: era "templates/encomenda/formulario"
        }
        try {
            Encomenda encomendaSalva = encomendaService.salvarOuAtualizar(dto);
            String mensagem = dto.id() != null
                    ? "Encomenda '" + encomendaSalva.getId() + "' atualizada com sucesso!"
                    : "Encomenda '" + encomendaSalva.getId() + "' criada com sucesso!";
            redirectAttributes.addFlashAttribute("message", mensagem);
            return "redirect:/encomenda";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            model.addAttribute("produtos", produtoService.procurarTodos());
            return "redirect:/encomenda/formulario" + (dto.id() != null ? "?id=" + dto.id() : "");
        }
    }

    @GetMapping("/delete/{id}")
    @Transactional
    public String deleteEncomenda(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            encomendaService.apagarPorId(id);
            redirectAttributes.addFlashAttribute("message", "A encomenda " + id + " foi apagada!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/encomenda";
    }

    // ========== NOVOS MÉTODOS PARA SOLICITAÇÃO DE ENCOMENDA ==========

    @GetMapping("/solicitar/{produtoId}")
    public String solicitarEncomenda(@PathVariable Integer produtoId, Model model) {
        try {
            Produto produto = produtoService.procurarPorId(produtoId)
                    .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

            model.addAttribute("produto", produto);
            model.addAttribute("caixasDisponiveis", caixaService.procurarTodos());

            return "encomenda/solicitar";

        } catch (EntityNotFoundException e) {
            return "redirect:/produto?error=Produto não encontrado";
        }
    }

    @PostMapping("/api/calcular-distancia")
    @ResponseBody
    public Map<String, Object> calcularDistancia(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            String origem = request.get("origem");
            String destino = request.get("destino");
            Integer produtoId = Integer.parseInt(request.get("produtoId"));
            Long caixaId = Long.parseLong(request.get("caixaId"));

            // Simulação de cálculo (substituir por API real)
            double distancia = calcularDistanciaAPI(origem, destino);
            double preco = calcularPreco(distancia, produtoId, caixaId);
            String tempo = calcularTempoEstimado(distancia);

            response.put("success", true);
            response.put("distancia", String.format("%.1f", distancia));
            response.put("preco", preco);
            response.put("tempo", tempo);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Erro no cálculo: " + e.getMessage());
        }

        return response;
    }

    private String calcularTempoEstimado(double distancia) {
        // Velocidade média considerando trânsito, paradas e condições da estrada
        // Caminhão: velocidade média entre 60-80 km/h em rodovias
        double velocidadeMedia = 65.0; // km/h

        // Calcular horas base
        double horasBase = distancia / velocidadeMedia;

        // Paradas para descanso (30 minutos a cada 4 horas de viagem)
        double paradasDescanso = Math.floor(horasBase / 4.0) * 0.5; // 0.5 horas = 30 min

        // Tempo adicional para tráfego urbano (15 min na origem + 15 min no destino)
        double tempoUrbano = 0.5; // 0.5 horas = 30 min

        // Margem de segurança para imprevistos (10% do tempo)
        double margemSeguranca = horasBase * 0.1;

        // Tempo total em horas
        double horasTotal = horasBase + paradasDescanso + tempoUrbano + margemSeguranca;

        // Converter para formato legível
        return formatarTempo(horasTotal);
    }

    private String formatarTempo(double horasTotal) {
        if (horasTotal < 1) {
            // Menos de 1 hora - mostrar em minutos
            int minutos = (int) Math.round(horasTotal * 60);
            if (minutos < 1) {
                return "Menos de 1 minuto";
            } else if (minutos == 1) {
                return "1 minuto";
            } else {
                return minutos + " minutos";
            }
        } else if (horasTotal < 24) {
            // Menos de 24 horas - mostrar em horas
            int horasInteiras = (int) Math.floor(horasTotal);
            int minutos = (int) Math.round((horasTotal - horasInteiras) * 60);

            if (minutos == 0) {
                if (horasInteiras == 1) {
                    return "1 hora";
                } else {
                    return horasInteiras + " horas";
                }
            } else {
                if (horasInteiras == 1) {
                    return "1 hora e " + minutos + " minutos";
                } else {
                    return horasInteiras + " horas e " + minutos + " minutos";
                }
            }
        } else {
            // Mais de 24 horas - mostrar em dias
            double dias = horasTotal / 24.0;
            if (dias < 2) {
                return String.format("%.1f dia", dias);
            } else {
                return String.format("%.1f dias", dias);
            }
        }
    }

    private double calcularPreco(double distancia, Integer produtoId, Long caixaId) {
        try {
            // Buscar informações do produto e caixa para cálculo mais preciso
            Produto produto = produtoService.procurarPorId(produtoId)
                    .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
            Caixa caixa = caixaService.procurarPorId(caixaId)
                    .orElseThrow(() -> new EntityNotFoundException("Caixa não encontrada"));

            // Cálculo por quilômetro rodado
            double precoPorKm = distancia * 1.80; // R$ 0,50 por km

            // Cálculo por fator de cubagem
            // Fórmula comum: Peso Cubado = Volume (m³) × Fator de Cubagem (300 kg/m³)
            double pesoCubado = produto.getVolume().doubleValue() * 300.0;

            // Usar o maior entre peso real e peso cubado
            double pesoParaCalculo = Math.max(produto.getPeso().doubleValue(), pesoCubado);

            // Preço por cubagem: R$ 2,00 por kg do peso de cálculo
            double precoPorCubagem = pesoParaCalculo * 2.0;

            // Preço base fixo
            double precoBase = 15.0;

            // Aplica a regra: maior valor entre km rodado ou fator de cubagem
            double precoFinal;
            if (precoPorKm > precoPorCubagem) {
                precoFinal = precoBase + precoPorKm;
            } else {
                precoFinal = precoBase + precoPorCubagem;
            }

            return precoFinal;

        } catch (Exception e) {
            // Fallback simples
            double precoPorKm = distancia * 0.50;
            double precoBase = 15.0;
            return precoBase + precoPorKm;
        }
    }

    @PostMapping("/confirmar")
    public String confirmarEncomenda(@RequestParam Long caixaId,
                                     @RequestParam Integer produtoId,
                                     @RequestParam String origem,
                                     @RequestParam String destino,
                                     @RequestParam Double distancia,
                                     @RequestParam Double preco,
                                     RedirectAttributes redirectAttributes) {
        try {
            // Cria a encomenda automaticamente
            Encomenda encomendaCriada = encomendaService.criarEncomendaAutomatica(
                    produtoId, caixaId, origem, destino, distancia, preco
            );

            redirectAttributes.addFlashAttribute("message",
                    "Encomenda #" + encomendaCriada.getId() + " criada com sucesso!");
            return "redirect:/encomenda";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Erro ao criar encomenda: " + e.getMessage());
            return "redirect:/encomenda/solicitar/" + produtoId;
        }
    }

    private double calcularDistanciaAPI(String origem, String destino) {
        try {
            // Codificar os parâmetros para URL
            String origemCodificada = URLEncoder.encode(origem, StandardCharsets.UTF_8.toString());
            String destinoCodificada = URLEncoder.encode(destino, StandardCharsets.UTF_8.toString());

            // Sua chave da API Distance Matrix
            String apiKey = "MUmLt0WfPDArWgpp4IwK7qLskgKDwWNyqX6LTikUuTbj5AhVTYSzxIlywgGxEpEE";

            // Construir a URL da API
            String url = String.format(
                    "https://api.distancematrix.ai/maps/api/distancematrix/json?origins=%s&destinations=%s&key=%s",
                    origemCodificada, destinoCodificada, apiKey
            );

            // Fazer a requisição HTTP
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

            // Processar a resposta
            Map<String, Object> responseBody = response.getBody();

            if (responseBody != null && "OK".equals(responseBody.get("status"))) {
                Map<String, Object> rows = ((java.util.List<Map<String, Object>>) responseBody.get("rows")).get(0);
                Map<String, Object> elements = ((java.util.List<Map<String, Object>>) rows.get("elements")).get(0);

                if ("OK".equals(elements.get("status"))) {
                    Map<String, Object> distance = (Map<String, Object>) elements.get("distance");
                    // Retornar a distância em metros e converter para km
                    Integer distanciaMetros = (Integer) distance.get("value");
                    return distanciaMetros / 1000.0; // Converter para km
                }
            }

            throw new RuntimeException("Não foi possível calcular a distância");

        } catch (Exception e) {
            // Em caso de erro na API, usar cálculo fallback
            System.err.println("Erro na API de distância: " + e.getMessage());
            return calcularDistanciaFallback(origem, destino);
        }
    }

    // Metodo fallback caso a API falhe
    private double calcularDistanciaFallback(String origem, String destino) {
        // Simulação básica baseada em cidades conhecidas
        Map<String, double[]> coordenadas = new HashMap<>();
        coordenadas.put("são paulo", new double[]{-23.5505, -46.6333});
        coordenadas.put("rio de janeiro", new double[]{-22.9068, -43.1729});
        coordenadas.put("porto alegre", new double[]{-30.0346, -51.2177});
        coordenadas.put("brasília", new double[]{-15.7797, -47.9297});
        coordenadas.put("belo horizonte", new double[]{-19.9167, -43.9345});

        String origemLower = origem.toLowerCase();
        String destinoLower = destino.toLowerCase();

        if (coordenadas.containsKey(origemLower) && coordenadas.containsKey(destinoLower)) {
            double[] coordOrigem = coordenadas.get(origemLower);
            double[] coordDestino = coordenadas.get(destinoLower);
            return calcularDistanciaHaversine(coordOrigem[0], coordOrigem[1], coordDestino[0], coordDestino[1]);
        }

        // Fallback genérico
        return Math.random() * 1000 + 50;
    }

    // Cálculo de distância usando fórmula de Haversine (fallback)
    private double calcularDistanciaHaversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Raio da Terra em km

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }
}