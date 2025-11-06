package com.example.frota.caixa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface CaixaRepository extends JpaRepository<Caixa, Long>{
	
	@Query(value = "SELECT c FROM Caixa c "
				 + "WHERE c.comprimento >= ?1 "
				 + "AND c.largura >= ?2 "
				 + "AND c.altura >= ?3 "
				 + "AND c.limitePeso >= ?4 "
				 + "ORDER BY c.volume ASC",  nativeQuery = true)
	public List<Caixa> findCaixasPossiveis(double comprimento, double largura, double altura, double peso);
}
