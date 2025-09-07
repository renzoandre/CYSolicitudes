package com.bootcamp.usecase.application;

import com.bootcamp.model.application.Application;
import com.bootcamp.model.application.gateways.ApplicationRepository;
import com.bootcamp.model.stateapplication.gateways.StateApplicationRepository;
import com.bootcamp.usecase.loantype.LoanTypeUseCase;
import com.bootcamp.usecase.stateapplication.StateApplicationUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import reactor.core.publisher.Mono;

@Log
@RequiredArgsConstructor
public class ApplicationUseCase {
    private final ApplicationRepository applicationRepository;
    private final LoanTypeUseCase loanTypeUseCase;
    private final StateApplicationUseCase stateApplicationUseCase;

    public Mono<Application> saveApplication(Application application) {
        log.info("Use Case saveApplication: " + application);
        application.setStateCode("PEN");

        return addLoanTypeId(application)
                .flatMap(this::addStateApplicationId)
                .flatMap(applicationRepository::saveApplication);
    }

    private Mono<Application> addLoanTypeId(Application app) {
        return loanTypeUseCase.findLoanTypeByCode(app.getLoanTypeCode())
                .map(loanType -> {
                    app.setLoanTypeId(loanType.getId());
                    return app;
                });
    }

    private Mono<Application> addStateApplicationId(Application app) {
        return stateApplicationUseCase.findStateApplicationByCode(app.getStateCode())
                .map(state -> {
                    app.setStateId(state.getId());
                    return app;
                });
    }

}
