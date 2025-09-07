package com.bootcamp.usecase.stateapplication;

import com.bootcamp.model.stateapplication.StateApplication;
import com.bootcamp.model.stateapplication.gateways.StateApplicationRepository;
import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@Log
@Repository
public class StateApplicationTest {

    private StateApplicationRepository stateApplicationRepository;
    private StateApplicationUseCase stateApplicationUseCase;

    @BeforeEach
    void setUp() {
        stateApplicationRepository = mock(StateApplicationRepository.class);
        stateApplicationUseCase = new StateApplicationUseCase(stateApplicationRepository);
    }

    @Test
    void shouldFindStateApplicationByCodeTest() {
        StateApplication stateApplication = StateApplication.builder()
                .code("PEN")
                .active(true)
                .build();

        when(stateApplicationRepository.findStateApplicationByCode(stateApplication.getCode()))
                .thenReturn(Mono.just(stateApplication));

        StepVerifier.create(stateApplicationUseCase.findStateApplicationByCode(stateApplication.getCode()))
                .expectNextMatches(loanFound -> loanFound.getCode().equals("PEN"))
                .verifyComplete();
    }

    @Test
    void shouldNotFindLoanTypeByCodeTest() {
        StateApplication stateApplication = StateApplication.builder()
                .code("ANY")
                .active(true)
                .build();

        when(stateApplicationRepository.findStateApplicationByCode(stateApplication.getCode()))
                .thenReturn(Mono.empty());

        StepVerifier.create(stateApplicationUseCase.findStateApplicationByCode(stateApplication.getCode()))
                .expectError()
                .verify();
    }

    @Test
    void shouldFindLoanTypeByIdTest() {
        StateApplication stateApplication = StateApplication.builder()
                .id(UUID.fromString("03608374-1877-4623-af3a-700a0f822900"))
                .active(true)
                .build();

        when(stateApplicationRepository.findStateApplicationById(stateApplication.getId()))
                .thenReturn(Mono.just(stateApplication));

        StepVerifier.create(stateApplicationUseCase.findStateApplicationById(stateApplication.getId()))
                .expectNextMatches(stateFound -> stateFound.getId().equals(UUID.fromString("03608374-1877-4623-af3a-700a0f822900")))
                .verifyComplete();
    }

    @Test
    void shouldNotFindLoanTypeByIdTest() {
        StateApplication stateApplication = StateApplication.builder()
                .id(UUID.fromString("03608374-1877-4623-af3a-700a0f822900"))
                .active(true)
                .build();

        when(stateApplicationRepository.findStateApplicationById(stateApplication.getId()))
                .thenReturn(Mono.empty());

        StepVerifier.create(stateApplicationUseCase.findStateApplicationById(stateApplication.getId()))
                .expectError()
                .verify();
    }

}
