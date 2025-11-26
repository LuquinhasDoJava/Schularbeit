package com.example.frota.caminhao;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import com.example.frota.marca.Marca;
import com.example.frota.marca.MarcaService;
import com.example.frota.manutencao.Manutencao;
import com.example.frota.manutencao.ManutencaoRepository;

@Service
public class CaminhaoService {

    @Autowired
    private CaminhaoRepository caminhaoRepository;

    @Autowired
    private MarcaService marcaService;

    @Autowired
    private CaminhaoMapper caminhaoMapper;

    @Autowired
    private ManutencaoRepository manutencaoRepository;

    public Caminhao salvarOuAtualizar(AtualizacaoCaminhao dto) {
        Marca marca = marcaService.procurarPorId(dto.marcaId())
                .orElseThrow(() -> new EntityNotFoundException("Marca não encontrada com ID: " + dto.marcaId()));

        if (dto.id() != null) {
            Caminhao existente = caminhaoRepository.findById(dto.id())
                    .orElseThrow(() -> new EntityNotFoundException("Caminhão não encontrado com ID: " + dto.id()));
            caminhaoMapper.updateEntityFromDto(dto, existente);
            existente.setMarca(marca);
            atualizarStatusManutencao(existente);
            return caminhaoRepository.save(existente);
        } else {
            Caminhao novoCaminhao = caminhaoMapper.toEntityFromAtualizacao(dto);
            novoCaminhao.setMarca(marca);
            atualizarStatusManutencao(novoCaminhao);
            return caminhaoRepository.save(novoCaminhao);
        }
    }

    public void registrarQuilometragem(Long caminhaoId, double novaQuilometragem) {
        Caminhao caminhao = caminhaoRepository.findById(caminhaoId)
                .orElseThrow(() -> new EntityNotFoundException("Caminhão não encontrado"));

        caminhao.setQuilometragemAtual(novaQuilometragem);
        atualizarStatusManutencao(caminhao);
        caminhaoRepository.save(caminhao);
    }

    public void realizarManutencaoRotina(Long caminhaoId, String descricao, double custo) {
        Caminhao caminhao = caminhaoRepository.findById(caminhaoId)
                .orElseThrow(() -> new EntityNotFoundException("Caminhão não encontrado"));

        Manutencao manutencao = new Manutencao();
        manutencao.setCaminhao(caminhao);
        manutencao.setDataManutencao(java.time.LocalDate.now());
        manutencao.setQuilometragem(caminhao.getQuilometragemAtual());
        manutencao.setTipoManutencao(Manutencao.TipoManutencao.ROTINA);
        manutencao.setDescricao("Troca de óleo, filtros e pastilhas - " + descricao);
        manutencao.setCusto(custo);

        caminhao.setQuilometragemUltimaManutencao(caminhao.getQuilometragemAtual());
        atualizarStatusManutencao(caminhao);

        manutencaoRepository.save(manutencao);
        caminhaoRepository.save(caminhao);
    }

    public void realizarTrocaPneus(Long caminhaoId, String descricao, double custo) {
        Caminhao caminhao = caminhaoRepository.findById(caminhaoId)
                .orElseThrow(() -> new EntityNotFoundException("Caminhão não encontrado"));

        Manutencao manutencao = new Manutencao();
        manutencao.setCaminhao(caminhao);
        manutencao.setDataManutencao(java.time.LocalDate.now());
        manutencao.setQuilometragem(caminhao.getQuilometragemAtual());
        manutencao.setTipoManutencao(Manutencao.TipoManutencao.PNEUS);
        manutencao.setDescricao("Troca de pneus - " + descricao);
        manutencao.setCusto(custo);

        caminhao.setQuilometragemUltimaTrocaPneus(caminhao.getQuilometragemAtual());
        atualizarStatusManutencao(caminhao);

        manutencaoRepository.save(manutencao);
        caminhaoRepository.save(caminhao);
    }

    private void atualizarStatusManutencao(Caminhao caminhao) {
        if (caminhao.isPrecisaManutencaoRotina() || caminhao.isPrecisaTrocaPneus()) {
            caminhao.setStatusManutencao(Caminhao.StatusManutencao.MANUTENCAO_PROXIMA);
        } else {
            caminhao.setStatusManutencao(Caminhao.StatusManutencao.EM_DIA);
        }
    }

    public List<Caminhao> procurarTodos() {
        return caminhaoRepository.findAll(Sort.by("modelo").ascending());
    }

    public void apagarPorId(Long id) {
        caminhaoRepository.deleteById(id);
    }

    public Optional<Caminhao> procurarPorId(Long id) {
        return caminhaoRepository.findById(id);
    }

    public List<Caminhao> procurarPrecisandoManutencao() {
        return caminhaoRepository.findByStatusManutencao(Caminhao.StatusManutencao.MANUTENCAO_PROXIMA);
    }
}