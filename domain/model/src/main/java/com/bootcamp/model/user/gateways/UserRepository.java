package com.bootcamp.model.user.gateways;

import com.bootcamp.model.user.User;
import com.bootcamp.model.user.UserResponse;
import com.bootcamp.model.user.Login;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<UserResponse> login(Login login);
    Mono<UserResponse> validateTokenClient(String token, String documentNumber);
    Mono<UserResponse> validateTokenAssessor(String token);
    Mono<UserResponse> findUserByDocumentNumber(String token, String documentNumber);
}
