package com.telemedicine.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GroqService {

    @Value("${groq.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String chat(String message) {

        String url = "https://api.groq.com/openai/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = Map.of(
                "model", "llama-3.1-8b-instant",

                "messages", List.of(

                        Map.of(
                                "role", "system",
                                "content",
                                "You are a professional telemedicine assistant. " +
                                "Answer only health-related questions. " +
                                "Provide clear, simple, and concise responses. " +
                                "Limit every answer to 4-6 sentences. " +
                                "Never prescribe medicines or make a definitive diagnosis. " +
                                "Suggest general self-care tips when appropriate. " +
                                "Always advise the user to consult a qualified healthcare professional if symptoms are severe, persistent, or life-threatening."
                        ),

                        Map.of(
                                "role", "user",
                                "content", message
                        )
                ),

                "temperature", 0.3,
                "max_tokens", 200
        );

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(body, headers);

        Map<String, Object> response =
                restTemplate.postForObject(url, entity, Map.class);

        List<Map<String, Object>> choices =
                (List<Map<String, Object>>) response.get("choices");

        Map<String, Object> choice = choices.get(0);

        Map<String, Object> messageMap =
                (Map<String, Object>) choice.get("message");

        return messageMap.get("content").toString().trim();
    }
}