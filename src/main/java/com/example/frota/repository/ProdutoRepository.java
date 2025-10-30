package com.example.frota.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.frota.entity.Produto;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface ProdutoRepository extends JpaRepository<Produto, Long>{

}