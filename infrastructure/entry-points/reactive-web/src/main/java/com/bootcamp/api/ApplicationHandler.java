package com.bootcamp.api;

import com.bootcamp.api.dto.ApplicationFilterDto;
import com.bootcamp.api.dto.CreateApplicationDto;
import com.bootcamp.api.exception.ValidationDtoException;
import com.bootcamp.api.helper.BuildApiResponseHelper;
import com.bootcamp.api.mapper.ApplicationDtoMapper;
import com.bootcamp.api.validator.RequestValidator;
import com.bootcamp.usecase.application.ApplicationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ApplicationHandler {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private final ApplicationUseCase applicationUseCase;
    private final ApplicationDtoMapper applicationDtoMapper;
    private final RequestValidator requestValidator;

    public Mono<ServerResponse> saveApplication(ServerRequest serverRequest) {
        String token = serverRequest.headers().firstHeader(HttpHeaders.AUTHORIZATION);
        String cleanToken = token != null && token.startsWith("Bearer ")
                ? token.substring(7)
                : null;

        if (cleanToken == null) {
            return BuildApiResponseHelper.buildError(new RuntimeException("Token no enviado"), HttpStatus.UNAUTHORIZED);
        }

        return serverRequest.bodyToMono(CreateApplicationDto.class)
                .flatMap(requestValidator::validate)
                .map(applicationDtoMapper::toModel)
                //.flatMap(applicationUseCase::saveApplication)
                .flatMap(application -> applicationUseCase.saveApplication(application, cleanToken))
                .map(applicationDtoMapper::toResponse)
                .flatMap(BuildApiResponseHelper::buildSuccess)
                .onErrorResume(
                        ValidationDtoException.class, ex -> BuildApiResponseHelper.buildError(ex, HttpStatus.BAD_REQUEST)
                )
                .onErrorResume(
                        RuntimeException.class, ex -> BuildApiResponseHelper.buildError(ex, HttpStatus.INTERNAL_SERVER_ERROR)
                );
                //.contextWrite(AuthContext.withToken(cleanToken));
    }

    public Mono<ServerResponse> findApplications(ServerRequest serverRequest) {
        String token = serverRequest.headers().firstHeader(HttpHeaders.AUTHORIZATION);
        String cleanToken = token != null && token.startsWith("Bearer ")
                ? token.substring(7)
                : null;

        if (cleanToken == null) {
            return BuildApiResponseHelper.buildError(new RuntimeException("Token no enviado"), HttpStatus.UNAUTHORIZED);
        }

        return serverRequest.bodyToMono(ApplicationFilterDto.class)
                .map(applicationDtoMapper::toModel)
                .flatMapMany(applicationFilter -> applicationUseCase.findApplications(applicationFilter, cleanToken))
                .map(applicationDtoMapper::toResponse)
                .flatMap(BuildApiResponseHelper::buildSuccess)
                .next()
                .onErrorResume(
                        ValidationDtoException.class, ex -> BuildApiResponseHelper.buildError(ex, HttpStatus.BAD_REQUEST)
                )
                .onErrorResume(
                        RuntimeException.class, ex -> BuildApiResponseHelper.buildError(ex, HttpStatus.INTERNAL_SERVER_ERROR)
                );
    }
}
