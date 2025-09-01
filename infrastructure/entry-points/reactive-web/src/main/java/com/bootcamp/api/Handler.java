package com.bootcamp.api;

import com.bootcamp.api.dto.CreateApplicationDto;
import com.bootcamp.api.exception.ValidationDtoException;
import com.bootcamp.api.helper.BuildApiResponseHelper;
import com.bootcamp.api.mapper.ApplicationDtoMapper;
import com.bootcamp.api.validator.RequestValidator;
import com.bootcamp.usecase.application.ApplicationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private final ApplicationUseCase applicationUseCase;
    private final ApplicationDtoMapper applicationDtoMapper;
    private final RequestValidator requestValidator;

    public Mono<ServerResponse> saveApplication(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CreateApplicationDto.class)
                .flatMap(requestValidator::validate)
                .map(applicationDtoMapper::toModel)
                .flatMap(applicationUseCase::saveApplication)
                .map(applicationDtoMapper::toResponse)
                .flatMap(BuildApiResponseHelper::buildSuccess)
                .onErrorResume(
                        ValidationDtoException.class, ex -> BuildApiResponseHelper.buildError(ex, HttpStatus.BAD_REQUEST)
                )
                // .onErrorResume(
                //        UserExistException.class, ex -> BuildApiResponseHelper.buildError(ex, HttpStatus.CONFLICT)
                // )
                .onErrorResume(
                        RuntimeException.class, ex -> BuildApiResponseHelper.buildError(ex, HttpStatus.INTERNAL_SERVER_ERROR)
                );
    }
}
