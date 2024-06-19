package com.example.monolithtomicroservices.application.auth;

import com.example.monolithtomicroservices.application.common.UseCase;
import com.example.monolithtomicroservices.domain.User;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserUseCase implements UseCase<RegisterUserRequest, User> {
    private final GetUserPort getUserPort;
    private final SaveUserPort saveUserPort;

    public RegisterUserUseCase(GetUserPort getUserPort, SaveUserPort saveUserPort) {
        this.getUserPort = getUserPort;
        this.saveUserPort = saveUserPort;
    }

    public User handle(RegisterUserRequest request) {
        final var user = User.builder()
                .username(request.username())
                .password(request.password())
                .email(request.email())
                .name(request.name())
                .build();

        getUserPort.byUsername(user.username())
                .ifPresent(existingUser -> {
                    throw new IllegalArgumentException("Username already exists");
                });

        getUserPort.byEmail(user.email())
                .ifPresent(existingUser -> {
                    throw new IllegalArgumentException("Email already exists");
                });

        return saveUserPort.save(user);
    }
}
