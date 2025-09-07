package com.bootcamp.usecase.stateapplication;

import com.bootcamp.model.stateapplication.StateApplication;
import com.bootcamp.model.stateapplication.gateways.StateApplicationRepository;
import com.bootcamp.usecase.exception.StateApplicationNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Log
@RequiredArgsConstructor
public class StateApplicationUseCase {
    private final StateApplicationRepository stateApplicationRepository;

    public Mono<StateApplication> findStateApplicationByCode(String code) {
        log.info("Use Case findStateApplicationByCode");
        return stateApplicationRepository.findStateApplicationByCode(code)
                .switchIfEmpty(Mono.error(
                        new StateApplicationNotFoundException("El código de estado " + code + " no existe en el sistema")
                ));
    }

    public Mono<StateApplication> findStateApplicationById(UUID id) {
        log.info("Use Case findStateApplicationById");
        return stateApplicationRepository.findStateApplicationById(id)
                .switchIfEmpty(Mono.error(
                        new StateApplicationNotFoundException("El identificador de estado " + id + " no existe en el sistema")
                ));
    }
}
