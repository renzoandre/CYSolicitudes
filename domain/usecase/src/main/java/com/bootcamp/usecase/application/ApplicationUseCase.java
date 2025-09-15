package com.bootcamp.usecase.application;

import com.bootcamp.model.application.Application;
import com.bootcamp.model.application.ApplicationFilter;
import com.bootcamp.model.application.ApplicationFilteredResponse;
import com.bootcamp.model.application.gateways.ApplicationRepository;
import com.bootcamp.model.loantype.LoanType;
import com.bootcamp.model.loantype.gateways.LoanTypeRepository;
import com.bootcamp.model.stateapplication.StateApplication;
import com.bootcamp.model.stateapplication.gateways.StateApplicationRepository;
import com.bootcamp.model.user.User;
import com.bootcamp.model.user.gateways.UserRepository;
import com.bootcamp.usecase.exception.RoleNotAllowedException;
import com.bootcamp.usecase.loantype.LoanTypeUseCase;
import com.bootcamp.usecase.stateapplication.StateApplicationUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@Log
@RequiredArgsConstructor
public class ApplicationUseCase {
    private final ApplicationRepository applicationRepository;
    private final LoanTypeUseCase loanTypeUseCase;
    private final StateApplicationUseCase stateApplicationUseCase;
    private final UserRepository userRepository;

    public Mono<Application> saveApplication(Application application, String token) {
        log.info("Use Case saveApplication: " + application);
        application.setStateCode("PEN");

        /*
        return addLoanTypeId(application)
                .flatMap(this::addStateApplicationId)
                .flatMap(applicationRepository::saveApplication);
        */

        return userRepository.validateTokenClient(token, application.getDocumentNumber())
                .flatMap(userResponse -> {
                    if (userResponse.isSuccess()) {
                        return addLoanTypeId(application)
                                .flatMap(this::addStateApplicationId)
                                .flatMap(applicationRepository::saveApplication);
                    } else {
                        return Mono.error(new RoleNotAllowedException(userResponse.getMessage()));
                    }
                });
    }

    public Flux<ApplicationFilteredResponse> findApplications(ApplicationFilter applicationFilter, String token) {
        /*
        Flux<Application> applications = loanTypeRepository.findLoanTypeByCode(applicationFilter.getLoanType())
                        .flatMapMany(loanType ->
                                stateApplicationRepository.findStateApplicationByCode(applicationFilter.getStateApplication())
                                        .flatMapMany(stateApplication ->
                                                applicationRepository.findApplicationsFilter(
                                                        applicationFilter.getDocumentNumber(),
                                                        loanType.getId(),
                                                        stateApplication.getId()
                                                )
                                        )
                        );*/

        /*
        Flux<Application> applications =
                Mono.justOrEmpty(applicationFilter.getLoanType())
                        .flatMap(loanTypeRepository::findLoanTypeByCode)
                        .map(loanType -> Optional.of(loanType.getId()))
                        .defaultIfEmpty(Optional.empty()) // sin nulls
                        .flatMapMany(optionalLoanTypeId ->
                                Mono.justOrEmpty(applicationFilter.getStateApplication())
                                        .flatMap(stateApplicationRepository::findStateApplicationByCode)
                                        .map(state -> Optional.of(state.getId()))
                                        .defaultIfEmpty(Optional.empty())
                                        .flatMapMany(optionalStateId ->
                                                applicationRepository.findApplicationsFilter(
                                                        applicationFilter.getDocumentNumber(),
                                                        optionalLoanTypeId.orElse(null),
                                                        optionalStateId.orElse(null)
                                                )
                                        )
                        );
        */

        /*
        Flux<Application> applications = applicationRepository.findApplicationsFilter(
                applicationFilter.getDocumentNumber(),
                null,
                UUID.fromString(applicationFilter.getStateApplication())
        );

        return applications.flatMap(application ->
                userRepository.findUserByDocumentNumber(token, application.getDocumentNumber())
                        .map(userResponse -> ApplicationFilteredResponse.builder()
                                .email(((User) userResponse.getData()).getEmail())
                                .build()
                        )
        );
        */
        return applicationRepository.findApplicationsFilter(
                        applicationFilter.getDocumentNumber(),
                        null,
                        UUID.fromString(applicationFilter.getStateApplication())
                )
                .flatMap(application ->
                        userRepository.findUserByDocumentNumber(token, application.getDocumentNumber())
                                .map(userResponse -> {
                                    return ApplicationFilteredResponse.builder()
                                            .name(userResponse.getData().getEmail())
                                            .email(userResponse.getData().getEmail())
                                            .baseSalary(userResponse.getData().getBaseSalary())
                                            .amount(application.getAmount())
                                            .term(application.getTerm())
                                            .stateApplication(application.getStateCode())
                                            .loanType(application.getLoanTypeCode())
                                            .interestRate(15.0)
                                            .monthlyAmountApplication(application.getAmount() / application.getTerm() * (1 + 15/100))
                                            .build();
                                })
                )
                .switchIfEmpty(Mono.<ApplicationFilteredResponse>error(new RuntimeException("Solicitudes no encontradas")));
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
