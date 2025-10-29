package com.example.frota.repository;

import com.example.frota.entity.Caixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CaixaRepository extends JpaRepository<Caixa, Long> {


}
