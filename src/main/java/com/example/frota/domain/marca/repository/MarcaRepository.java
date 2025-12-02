package com.example.frota.domain.marca.repository;

import com.example.frota.domain.marca.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface MarcaRepository extends JpaRepository<Marca, Long>{
	
}
