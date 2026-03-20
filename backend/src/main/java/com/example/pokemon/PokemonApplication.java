package com.example.pokemon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Pokemon Team Analysis Tool backend.
 *
 * <p>This class boots the Spring Boot application and enables component scanning
 * for the controller, service, client, mapper, config, and exception layers.</p>
 */
@SpringBootApplication
public class PokemonApplication {

    /**
     * Starts the Spring Boot application.
     *
     * @param args command-line arguments passed to the JVM at startup
     */
    public static void main(String[] args) {
        SpringApplication.run(PokemonApplication.class, args);
    }
}
