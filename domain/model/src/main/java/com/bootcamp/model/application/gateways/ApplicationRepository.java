package com.bootcamp.model.application.gateways;

import com.bootcamp.model.application.Application;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ApplicationRepository {
    Mono<Application> saveApplication(Application application);
    Flux<Application> findApplicationsFilter(String documentNumber, UUID loanTypeId, UUID statusApplicationId);
}
