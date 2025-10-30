package com.example.frota.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class DistanceMatrixService {
	@Value ("${distancematrix.ai.api.url}")
	private String dMatrixApiUrl;

	@Value ("${distancematrix.ai.api.key}")
	private String apiKey;

	private final RestTemplate restTemplate;

	public DistanceMatrixService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public String calcularDistancia(String origem, String destino) {
		URI baseUri = URI.create(this.dMatrixApiUrl);
		String url = UriComponentsBuilder.fromUri(baseUri)
				.queryParam("origins", origem) 
                .queryParam("destinations", destino)
                .queryParam("key", this.apiKey)
				.toUriString();

		return restTemplate.getForObject(url, String.class);
		
	}

  
}
