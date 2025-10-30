package com.example.frota.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.frota.entity.Encomenda;
import com.example.frota.repository.EncomendaRepository;

@Service
public class EncomendaService {

    @Autowired
    private EncomendaRepository encomendaRepository;

    public Encomenda salvar(Encomenda encomenda) {
        return encomendaRepository.save(encomenda);
    }

    public List<Encomenda> procurarTodos(){
        return encomendaRepository.findAll();
    }
    public void apagarPorId (Long id) {
        encomendaRepository.deleteById(id);
    }

    public Optional<Encomenda> procurarPorId(Long id) {
        return encomendaRepository.findById(id);
    }
    public Encomenda atualizarEncomenda(Encomenda encomenda) {
        return encomendaRepository.save(encomenda);
    }
}