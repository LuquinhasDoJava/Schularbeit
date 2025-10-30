package com.example.frota.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.frota.entity.Encomenda;


@Repository
@Transactional
public interface EncomendaRepository extends JpaRepository<Encomenda, Long>{

}