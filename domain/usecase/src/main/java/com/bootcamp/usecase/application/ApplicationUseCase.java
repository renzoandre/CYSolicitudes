package com.bootcamp.usecase.application;

import com.bootcamp.model.application.Application;
import com.bootcamp.model.application.gateways.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import reactor.core.publisher.Mono;

@Log
@RequiredArgsConstructor
public class ApplicationUseCase {
    private final ApplicationRepository applicationRepository;

    public Mono<Application> saveApplication(Application application) {
        log.info("Use Case saveApplication");
        application.setState("PENDIENTE");
        application.setActive(true);
        return applicationRepository.saveApplication(application);
    }
}
