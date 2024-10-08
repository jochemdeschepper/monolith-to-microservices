package com.example.monolithtomicroservices.application.auth;

import com.example.monolithtomicroservices.application.cart.CreateCartPort;
import com.example.monolithtomicroservices.application.common.UseCase;
import com.example.monolithtomicroservices.domain.User;
import com.example.monolithtomicroservices.security.EncryptionService;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserUseCase implements UseCase<RegisterUserRequest, User> {
    private final EncryptionService encryptionService;
    private final GetUserPort getUserPort;
    private final SaveUserPort saveUserPort;
    private final CreateCartPort createCartPort;

    public RegisterUserUseCase(EncryptionService encryptionService,
                               GetUserPort getUserPort,
                               SaveUserPort saveUserPort,
                               CreateCartPort createCartPort) {
        this.encryptionService = encryptionService;
        this.getUserPort = getUserPort;
        this.saveUserPort = saveUserPort;
        this.createCartPort = createCartPort;
    }

    public User handle(RegisterUserRequest request) {
        final var encryptedPassword = encryptionService.encrypt(request.password());
        final var user = User.builder()
                .username(request.username())
                .password(encryptedPassword)
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

        final var savedUser = saveUserPort.save(user);
        createCartPort.create(savedUser);
        return savedUser;
    }
}
