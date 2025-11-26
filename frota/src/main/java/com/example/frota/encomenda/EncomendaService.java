package com.example.frota.encomenda;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import com.example.frota.caixa.Caixa;
import com.example.frota.caixa.CaixaService;
import com.example.frota.caminhao.Caminhao;
import com.example.frota.caminhao.CaminhaoService;
import com.example.frota.produto.ProdutoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.frota.produto.Produto;
import com.example.frota.produto.ProdutoService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EncomendaService {

    @Autowired
    private EncomendaRepository encomendaRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CaixaService caixaService;

    @Autowired
    private CaminhaoService caminhaoService;

    @Autowired
    private ProdutoMapper produtoMapper;

    @Autowired
    private EncomendaMapper encomendaMapper;

    public Encomenda salvarOuAtualizar(AtualizacaoEncomenda dto) {
        Produto produto = produtoService.procurarPorId(dto.produtoId())
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + dto.produtoId()));

        Encomenda encomenda;

        if (dto.id() != null) {
            encomenda = encomendaRepository.findById(dto.id())
                    .orElseThrow(() -> new EntityNotFoundException("Encomenda não encontrada com ID: " + dto.id()));
            encomendaMapper.updateEntityFromDto(dto, encomenda);
        } else {
            encomenda = encomendaMapper.toEntityFromAtualizacao(dto);
        }

        encomenda.setProduto(produto);

        // Designar caminhão automaticamente se não especificado
        if (dto.caminhaoId() == null && encomenda.getCaminhao() == null) {
            Caminhao caminhao = designarCaminhaoAutomatico(encomenda);
            encomenda.setCaminhao(caminhao);
        }

        return encomendaRepository.save(encomenda);
    }

    public Caminhao designarCaminhaoAutomatico(Encomenda encomenda) {
        List<Caminhao> caminhoesDisponiveis = caminhaoService.procurarTodos();

        // Filtra caminhões com manutenção em dia
        caminhoesDisponiveis = caminhoesDisponiveis.stream()
                .filter(c -> c.getStatusManutencao() == Caminhao.StatusManutencao.EM_DIA)
                .toList();

        // Encontra o caminhão com maior espaço interno disponível
        return caminhoesDisponiveis.stream()
                .max((c1, c2) -> Double.compare(c1.getVolume(), c2.getVolume()))
                .orElseThrow(() -> new EntityNotFoundException("Nenhum caminhão disponível encontrado"));
    }

    // MÉTODOS PARA ATUALIZAR STATUS DE ENTREGA
    public Encomenda atualizarStatus(Integer encomendaId, Encomenda.StatusEntrega novoStatus) {
        Encomenda encomenda = encomendaRepository.findById(encomendaId)
                .orElseThrow(() -> new EntityNotFoundException("Encomenda não encontrada"));

        encomenda.setStatusEntrega(novoStatus);

        // Atualiza timestamps conforme o status
        switch (novoStatus) {
            case COLETADA:
                encomenda.setDataColeta(LocalDateTime.now());
                break;
            case EM_PROCESSAMENTO:
                encomenda.setDataProcessamento(LocalDateTime.now());
                break;
            case A_CAMINHO:
                encomenda.setDataEnvio(LocalDateTime.now());
                break;
            case ENTREGUE:
                encomenda.setDataEntrega(LocalDateTime.now());
                break;
        }

        return encomendaRepository.save(encomenda);
    }


    public Encomenda atualizarLocalizacao(Integer encomendaId, Double latitude, Double longitude) {
        Encomenda encomenda = encomendaRepository.findById(encomendaId)
                .orElseThrow(() -> new EntityNotFoundException("Encomenda não encontrada"));

        encomenda.setLatitudeAtual(latitude);
        encomenda.setLongitudeAtual(longitude);

        return encomendaRepository.save(encomenda);
    }

    public Encomenda registrarFeedback(Integer encomendaId, Integer pontuacao, String comentario) {
        Encomenda encomenda = encomendaRepository.findById(encomendaId)
                .orElseThrow(() -> new EntityNotFoundException("Encomenda não encontrada"));

        if (!encomenda.getStatusEntrega().equals(Encomenda.StatusEntrega.ENTREGUE)) {
            throw new IllegalStateException("Só é possível avaliar encomendas entregues");
        }

        encomenda.setPontuacao(pontuacao);
        encomenda.setComentarioFeedback(comentario);

        return encomendaRepository.save(encomenda);
    }

    public List<Encomenda> procurarTodos() {
        return encomendaRepository.findAll(Sort.by("dataSolicitacao").descending());
    }

    public void apagarPorId(Integer id) {
        encomendaRepository.deleteById(id);
    }

    public Optional<Encomenda> procurarPorId(Integer id) {
        return encomendaRepository.findById(id);
    }

    public Encomenda criarEncomendaAutomatica(Integer produtoId, Long caixaId, String origem,
                                              String destino, Double distancia, Double preco, LocalTime horarioColeta) {
        Produto produto = produtoService.procurarPorId(produtoId)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        Caixa caixa = caixaService.procurarPorId(caixaId)
                .orElseThrow(() -> new EntityNotFoundException("Caixa não encontrada"));

        // Atualiza o produto com a caixa selecionada
        produto.setCaixa(caixa);
        produtoService.salvarOuAtualizar(produtoMapper.toAtualizacaoDto(produto));

        // Cria a encomenda com todos os campos
        Encomenda encomenda = new Encomenda();
        encomenda.setProduto(produto);
        encomenda.setOrigem(origem);
        encomenda.setDestino(destino);
        encomenda.setDistancia(BigDecimal.valueOf(distancia));
        encomenda.setPreco(BigDecimal.valueOf(preco));
        encomenda.setHorarioColeta(horarioColeta);

        // Designa caminhão automaticamente
        Caminhao caminhao = designarCaminhaoAutomatico(encomenda);
        encomenda.setCaminhao(caminhao);

        return encomendaRepository.save(encomenda);
    }

    // NOVO: Buscar encomendas por status
    public List<Encomenda> procurarPorStatus(Encomenda.StatusEntrega status) {
        return encomendaRepository.findByStatusEntrega(status);
    }

    // NOVO: Buscar encomendas para um caminhão específico
    public List<Encomenda> procurarPorCaminhao(Long caminhaoId) {
        return encomendaRepository.findByCaminhaoId(caminhaoId);
    }
}