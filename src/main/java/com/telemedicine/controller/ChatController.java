package com.telemedicine.controller;

import org.springframework.web.bind.annotation.*;

import com.telemedicine.model.ChatRequest;
import com.telemedicine.model.ChatResponse;
import com.telemedicine.service.GroqService;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin("*")
public class ChatController {

    private final GroqService groqService;

    public ChatController(GroqService groqService) {
        this.groqService = groqService;
    }

    @PostMapping
    public ChatResponse chat(@RequestBody ChatRequest request) {

        String reply = groqService.chat(request.getMessage());

        return new ChatResponse(reply);
    }
}