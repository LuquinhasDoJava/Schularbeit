package com.example.frota.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DistanceMatrixServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private DistanceMatrixService distanceMatrixService;

    // Para testar com propriedades, precisamos configurar via Reflection
    @Test
    void testCalcularDistancia_Success() {
        // Arrange
        String origem = "São Paulo, SP";
        String destino = "Rio de Janeiro, RJ";
        String expectedResponse = "{\"rows\":[{\"elements\":[{\"distance\":{\"text\":\"442 km\",\"value\":442000}}]}]}";

        // Configurar propriedades via Reflection
        setField(distanceMatrixService, "dMatrixApiUrl", "https://api.distancematrix.ai/maps/api/distancematrix/json");
        setField(distanceMatrixService, "apiKey", "test-key");

        when(restTemplate.getForObject(anyString(), eq(String.class)))
                .thenReturn(expectedResponse);

        // Act
        String result = distanceMatrixService.calcularDistancia(origem, destino);

        // Assert
        assertNotNull(result);
        assertEquals(expectedResponse, result);
        verify(restTemplate, times(1)).getForObject(anyString(), eq(String.class));
    }

    @Test
    void testCalcularDistancia_NullParameters() {
        // Arrange
        String origem = null;
        String destino = "Rio de Janeiro, RJ";

        setField(distanceMatrixService, "dMatrixApiUrl", "https://api.distancematrix.ai/maps/api/distancematrix/json");
        setField(distanceMatrixService, "apiKey", "test-key");

        String expectedResponse = "{\"error\": \"Invalid parameters\"}";
        when(restTemplate.getForObject(anyString(), eq(String.class)))
                .thenReturn(expectedResponse);

        // Act
        String result = distanceMatrixService.calcularDistancia(origem, destino);

        // Assert
        assertNotNull(result);
        verify(restTemplate, times(1)).getForObject(anyString(), eq(String.class));
    }

    @Test
    void testCalcularDistancia_EmptyParameters() {
        // Arrange
        String origem = "";
        String destino = "";

        setField(distanceMatrixService, "dMatrixApiUrl", "https://api.distancematrix.ai/maps/api/distancematrix/json");
        setField(distanceMatrixService, "apiKey", "test-key");

        String expectedResponse = "{\"error\": \"Empty parameters\"}";
        when(restTemplate.getForObject(anyString(), eq(String.class)))
                .thenReturn(expectedResponse);

        // Act
        String result = distanceMatrixService.calcularDistancia(origem, destino);

        // Assert
        assertNotNull(result);
        verify(restTemplate, times(1)).getForObject(anyString(), eq(String.class));
    }

    @Test
    void testCalcularDistancia_RestTemplateException() {
        // Arrange
        String origem = "São Paulo, SP";
        String destino = "Rio de Janeiro, RJ";

        setField(distanceMatrixService, "dMatrixApiUrl", "https://api.distancematrix.ai/maps/api/distancematrix/json");
        setField(distanceMatrixService, "apiKey", "test-key");

        when(restTemplate.getForObject(anyString(), eq(String.class)))
                .thenThrow(new RuntimeException("API Error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            distanceMatrixService.calcularDistancia(origem, destino);
        });

        verify(restTemplate, times(1)).getForObject(anyString(), eq(String.class));
    }

    // Método helper para configurar campos privados via Reflection
    private void setField(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set field: " + fieldName, e);
        }
    }
}