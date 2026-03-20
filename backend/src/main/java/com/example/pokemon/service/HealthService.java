package com.example.pokemon.service;

import com.example.pokemon.dto.HealthStatusResponse;
import org.springframework.stereotype.Service;

@Service
public class HealthService {

    public HealthStatusResponse getHealth() {
        return new HealthStatusResponse("ok");
    }
}
