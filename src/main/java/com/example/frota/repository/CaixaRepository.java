package com.example.frota.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.frota.entity.Caixa;

@Repository
@Transactional
public interface CaixaRepository extends JpaRepository<Caixa, Long>{
	
	@Query(value = "SELECT TOP 1 c.* FROM caixa c "
				 + "WHERE c.comprimento >= ?1 "
				 + "AND c.largura >= ?2 "
				 + "AND c.altura >= ?3 "
				 + "AND c.limitePeso >= ?4"
				 + "ORDER BY v"
				 + "c.volume ASC")
	public Caixa findCaixaMaisProxima(double comprimento, double largura, double altura, double peso);

  
}
