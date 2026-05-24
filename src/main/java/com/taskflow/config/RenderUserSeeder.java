package com.taskflow.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.taskflow.model.User;
import com.taskflow.repository.UserRepository;

@Component
@Profile("render")
public class RenderUserSeeder implements CommandLineRunner {

    private final UserRepository userRepository;

    public RenderUserSeeder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        String seedUsers = System.getenv("TASKFLOW_SEED_USERS");

        if (seedUsers == null || seedUsers.isBlank()) {
            return;
        }

        for (String row : seedUsers.split("\\r?\\n")) {
            if (row.isBlank()) {
                continue;
            }

            String[] fields = row.split("\\|", -1);
            if (fields.length != 4) {
                throw new IllegalArgumentException("Invalid TASKFLOW_SEED_USERS row. Expected email|name|passwordHash|role");
            }

            String email = fields[0].trim();
            String name = fields[1].trim();
            String passwordHash = fields[2].trim();
            String role = fields[3].trim();

            User user = userRepository.findByEmail(email);
            if (user == null) {
                user = new User();
                user.setEmail(email);
            }

            user.setName(name);
            user.setPassword(passwordHash);
            user.setRole(role);

            userRepository.save(user);
        }
    }
}
