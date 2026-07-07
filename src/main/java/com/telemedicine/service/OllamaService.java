package com.telemedicine.service;

import java.util.Map;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OllamaService {

    private final String OLLAMA_URL = "http://localhost:11434/api/generate";

    public String chat(String message) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> request = Map.of(
                "model", "llama3",
                "prompt", message,
                "stream", false
        );

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(request, headers);

        Map response = restTemplate.postForObject(
                OLLAMA_URL,
                entity,
                Map.class
        );

        return response.get("response").toString();
    }
}