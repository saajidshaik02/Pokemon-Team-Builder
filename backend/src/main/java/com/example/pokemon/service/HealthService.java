package com.example.pokemon.service;

import com.example.pokemon.dto.HealthResponse;
import org.springframework.stereotype.Service;

@Service
public class HealthService {

    public HealthResponse getHealth() {
        return new HealthResponse("ok");
    }
}
