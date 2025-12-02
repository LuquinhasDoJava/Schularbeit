package com.example.frota.infrastructure.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.frota.api.interceptors.ApiKeyInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	//registrando o interceptador
    @Autowired
    private ApiKeyInterceptor apiKeyInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiKeyInterceptor)
                .addPathPatterns("/**") // Aplica a todas as rotas /caminhao/** e /caixa/**
                .excludePathPatterns("/caminhao/public/**"); // Exclui rotas públicas
    }
//    .addPathPatterns("/aluno/**", "/batimentos/**") // Aplica a todas as rotas
    //.addPathPatterns("/aluno/**") // Aplica a rotas /aluno/**
    //.addPathPatterns("/**") // Aplica a todas as rotas
}
