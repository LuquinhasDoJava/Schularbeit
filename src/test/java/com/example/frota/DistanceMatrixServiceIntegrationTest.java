package com.example.frota.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(properties = {
        "distancematrix.ai.api.url=https://api.distancematrix.ai/maps/api/distancematrix/json",
        "distancematrix.ai.api.key=test-key-123"
})
class DistanceMatrixServiceIntegrationTest {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private DistanceMatrixService distanceMatrixService;

    @Test
    void testCalcularDistancia_Integration() {
        // Arrange
        String origem = "São Paulo, SP";
        String destino = "Rio de Janeiro, RJ";
        String expectedResponse = """
            {
                "rows": [{
                    "elements": [{
                        "distance": {
                            "text": "442 km",
                            "value": 442000
                        }
                    }]
                }]
            }
            """;

        when(restTemplate.getForObject(anyString(), eq(String.class)))
                .thenReturn(expectedResponse);

        // Act
        String result = distanceMatrixService.calcularDistancia(origem, destino);

        System.out.println(result);
        // Assert
        assertNotNull(result);
        assertTrue(result.contains("442 km"));
        verify(restTemplate, times(1)).getForObject(anyString(), eq(String.class));
    }
}