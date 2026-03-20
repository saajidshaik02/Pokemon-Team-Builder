package com.example.pokemon.service;

import com.example.pokemon.dto.HealthStatusResponse;
import org.springframework.stereotype.Service;

/**
 * Service that supplies the application health payload.
 *
 * <p>The implementation is intentionally simple because the project only needs a
 * lightweight backend health check for manual verification and smoke testing.</p>
 */
@Service
public class HealthService {

    /**
     * Returns the backend health status.
     *
     * @return a DTO containing the constant {@code ok} status value
     */
    public HealthStatusResponse getHealth() {
        return new HealthStatusResponse("ok");
    }
}
