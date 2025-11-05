package com.example.frota.encomenda;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface EncomendaRepository extends JpaRepository<Encomenda, Integer> {

}