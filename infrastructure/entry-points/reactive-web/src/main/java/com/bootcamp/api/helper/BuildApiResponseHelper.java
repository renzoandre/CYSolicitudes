package com.bootcamp.api.helper;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class BuildApiResponseHelper {
    private BuildApiResponseHelper() {}

    public static Mono<ServerResponse> buildSuccess(Object value) {
        ApiResponseHelper<Object> response = ApiResponseHelper.builder()
                .success(true)
                .message("")
                .data(value)
                .build();

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(response);
    }

    public static Mono<ServerResponse> buildError(RuntimeException ex, HttpStatus status) {
        ApiResponseHelper<Object> response = ApiResponseHelper.builder()
                .success(false)
                .message(ex.getMessage())
                .data("")
                .build();

        return ServerResponse.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(response);
    }
}
