package com.bootcamp.model.application.gateways;

import com.bootcamp.model.application.Application;
import reactor.core.publisher.Mono;

public interface ApplicationRepository {
    Mono<Application> saveApplication(Application application);
}
