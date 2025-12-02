package com.example.frota.api.interceptors;

import com.example.frota.api.annotations.PublicRoute;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Set;

@Component
public class ApiKeyInterceptor implements HandlerInterceptor {


    //solução que intercepta qualquer chamada nos controles
    //simples no controle de acesso, mas utiliza chaves estáticas
    private final Set<String> CHAVES_VALIDAS = Set.of(
            "cco123",
            "azul123",
            "frota-secret-key"
    );

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        if (handler instanceof HandlerMethod handlerMethod) {
            // Se o método tiver @PublicRoute, deixa passar
            if (handlerMethod.getMethodAnnotation(PublicRoute.class) != null) {
                return true;
            }
        }
        String apiKey = request.getHeader("X-API-KEY");

        if (apiKey == null || !isValidApiKey(apiKey)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("API Key invalida");
            return false;
        }

        return true;
    }

    private boolean isValidApiKey(String apiKey) {
        if (!CHAVES_VALIDAS.contains(apiKey))
            return false;
        return true;
    }
}