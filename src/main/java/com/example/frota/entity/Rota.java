package com.example.frota.entity;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Rota implements Serializable {

    private static final long serialVersionUID = 1L;

    private String origem;
    private String destino;
    private String distancia;
    private String tempo;

}