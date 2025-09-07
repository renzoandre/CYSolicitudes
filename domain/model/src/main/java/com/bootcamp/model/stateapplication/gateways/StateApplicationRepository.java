package com.bootcamp.model.stateapplication.gateways;

import com.bootcamp.model.stateapplication.StateApplication;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface StateApplicationRepository {
    Mono<StateApplication> findStateApplicationByCode(String code);
    Mono<StateApplication> findStateApplicationById(UUID id);
}
