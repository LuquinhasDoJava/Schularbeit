package com.example.frota.domain.solicitacao.repository;

import com.example.frota.domain.solicitacao.model.Solicitacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long>{
	
}
