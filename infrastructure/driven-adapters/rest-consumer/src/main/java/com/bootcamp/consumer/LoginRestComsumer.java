package com.bootcamp.consumer;

import com.bootcamp.model.application.ApplicationFilteredResponse;
import com.bootcamp.model.user.User;
import com.bootcamp.model.user.UserResponse;
import com.bootcamp.model.user.Login;
import com.bootcamp.model.user.gateways.UserRepository;
import com.google.gson.Gson;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;



@Service
@RequiredArgsConstructor
public class LoginRestComsumer implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(LoginRestComsumer.class);
    private final WebClient client;

    @PostConstruct
    public void init() {
        log.info("__LoginRestComsumer inicalizado");
    }

    @Override
    @CircuitBreaker(name = "login")
    public Mono<UserResponse> login(Login login) {
        LoginRequest request = LoginRequest.builder()
                .account(login.getAccount())
                .password(login.getPassword())
                .build();

        return client
                .post()
                .uri("/api/v1/login")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .doOnNext(response -> log.info("Respuesta login: {}", response)) // <-- log
                .onErrorMap(Exception.class, ex -> {
                        log.info("Errorrrr login: {}", ex.getMessage());
                        return new RuntimeException("Error en login" + ex.getMessage());
                })
                .onErrorMap(Exception.class,
                        ex -> {
                            log.error("💥 Error inesperado al login", ex);
                            return new RuntimeException("Error inesperado al login", ex);
                        });
    }

    @Override
    @CircuitBreaker(name = "validateTokenClient")
    public Mono<UserResponse> validateTokenClient(String token, String documentNumber) {
        /*
        return AuthContext.getToken()
                .flatMap(strToken -> client
                        .get()
                        .uri("/api/v1/validateClient/" + documentNumber)
                        .headers(headers -> headers.setBearerAuth(strToken))
                        //.bodyValue(request)
                        .retrieve()
                        .bodyToMono(UserResponse.class)
                        .doOnNext(response -> log.info("Respuesta validar cliente: {}", response)) // <-- log
                        .onErrorMap(Exception.class, ex -> {
                            log.info("Error en validar cliente: {}", ex.getMessage());
                            return new RuntimeException("Error en validar cliente" + ex.getMessage());
                        })
                        .onErrorMap(Exception.class,
                                ex -> {
                                    log.error("💥 Error inesperado al validar cliente", ex);
                                    return new RuntimeException("Error inesperado al validar cliente", ex);
                                })
                );
        */

        return client
                .get()
                .uri("/api/v1/validateClient/" + documentNumber)
                .headers(headers -> headers.setBearerAuth(token))
                //.bodyValue(request)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .doOnNext(response -> log.info("Respuesta validar cliente: {}", response)) // <-- log
                .onErrorMap(Exception.class, ex -> {
                    log.info("Error en validar cliente: {}", ex.getMessage());
                    return new RuntimeException("Error en validar cliente" + ex.getMessage());
                })
                .onErrorMap(Exception.class,
                        ex -> {
                            log.error("💥 Error inesperado al validar cliente", ex);
                            return new RuntimeException("Error inesperado al validar cliente", ex);
                        });
    }

    @Override
    @CircuitBreaker(name = "validateTokenAssessor")
    public Mono<UserResponse> validateTokenAssessor(String token) {
        return client
                .get()
                .uri("/api/v1/validateAssesor")
                .headers(headers -> headers.setBearerAuth(token))
                //.bodyValue(request)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .doOnNext(response -> log.info("Respuesta validar asesor: {}", response)) // <-- log
                .onErrorMap(Exception.class, ex -> {
                    log.info("Error en validar asesor: {}", ex.getMessage());
                    return new RuntimeException("Error en validar asesor" + ex.getMessage());
                })
                .onErrorMap(Exception.class,
                        ex -> {
                            log.error("💥 Error inesperado al validar asesor", ex);
                            return new RuntimeException("Error inesperado al validar asesor", ex);
                        });
    }

    @Override
    @CircuitBreaker(name = "findUserByDocumentNumber")
    public Mono<UserResponse> findUserByDocumentNumber(String token, String documentNumber) {
        return client
                .get()
                .uri("/api/v1/userByDocumentNumber/" + documentNumber)
                .headers(headers -> headers.setBearerAuth(token))
                //.bodyValue(request)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .doOnNext(response -> log.info("Respuesta encontrar usuario por documento: {}", response)) // <-- log
                .onErrorMap(Exception.class, ex -> {
                    log.info("Error encontrar usuario por documento: {}", ex.getMessage());
                    return new RuntimeException("Error encontrar usuario por documento" + ex.getMessage());
                })
                .onErrorMap(Exception.class,
                        ex -> {
                            log.error("💥 Error inesperado al encontrar usuario por documento", ex);
                            return new RuntimeException("Error inesperado al encontrar usuario por documento", ex);
                        });
    }

}
