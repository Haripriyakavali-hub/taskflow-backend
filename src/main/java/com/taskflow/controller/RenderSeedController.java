package com.taskflow.controller;

import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.taskflow.config.RenderUserSeeder;

@RestController
@Profile("render")
public class RenderSeedController {

    private final RenderUserSeeder renderUserSeeder;

    public RenderSeedController(RenderUserSeeder renderUserSeeder) {
        this.renderUserSeeder = renderUserSeeder;
    }

    @PostMapping("/internal/seed-users")
    public Map<String, Integer> seedUsers(@RequestHeader(name = "X-Seed-Token", required = false) String seedToken) {
        String expectedToken = System.getenv("TASKFLOW_SEED_TOKEN");

        if (expectedToken == null || expectedToken.isBlank() || !expectedToken.equals(seedToken)) {
            throw new InvalidSeedTokenException();
        }

        return Map.of("seeded", renderUserSeeder.seedUsers());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    private static class InvalidSeedTokenException extends RuntimeException {
    }
}
