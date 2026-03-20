package com.example.pokemon.controller;

import com.example.pokemon.dto.HealthStatusResponse;
import com.example.pokemon.service.HealthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for the health-check endpoint.
 *
 * <p>This controller exposes {@code GET /api/health} so clients can verify that the
 * backend is running and able to serve requests.</p>
 */
@RestController
@RequestMapping("/api/health")
public class HealthController {

    private final HealthService healthService;

    /**
     * Creates a new health controller.
     *
     * @param healthService service that supplies the health status payload
     */
    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    /**
     * Returns the current API health status.
     *
     * @return a small DTO describing the API status
     */
    @GetMapping
    public HealthStatusResponse getHealth() {
        return healthService.getHealth();
    }
}
