package com.example.pokemon.controller;

import com.example.pokemon.dto.HealthStatusResponse;
import com.example.pokemon.service.HealthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Health", description = "Health and smoke-test endpoints")
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
    @Operation(summary = "Get backend health", description = "Returns a lightweight backend health payload for smoke testing.")
    @ApiResponse(responseCode = "200", description = "Backend is available")
    public HealthStatusResponse getHealth() {
        return healthService.getHealth();
    }
}
