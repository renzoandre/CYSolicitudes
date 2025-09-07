package com.bootcamp.usecase.application;

import com.bootcamp.model.application.Application;
import com.bootcamp.model.application.gateways.ApplicationRepository;
import com.bootcamp.model.loantype.LoanType;
import com.bootcamp.model.loantype.gateways.LoanTypeRepository;
import com.bootcamp.model.stateapplication.StateApplication;
import com.bootcamp.model.stateapplication.gateways.StateApplicationRepository;
import com.bootcamp.usecase.loantype.LoanTypeUseCase;
import com.bootcamp.usecase.stateapplication.StateApplicationUseCase;
import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Log
@Repository
public class ApplicationTest {

    private ApplicationRepository applicationRepository;
    private ApplicationUseCase applicationUseCase;
    private LoanTypeUseCase loanTypeUseCase;
    private LoanTypeRepository loanTypeRepository;
    private StateApplicationRepository stateApplicationRepository;
    private StateApplicationUseCase stateApplicationUseCase;

    @BeforeEach
    void setUp() {
        applicationRepository = mock(ApplicationRepository.class);
        loanTypeRepository = mock(LoanTypeRepository.class);
        loanTypeUseCase = new LoanTypeUseCase(loanTypeRepository);
        stateApplicationRepository = mock(StateApplicationRepository.class);
        stateApplicationUseCase = new StateApplicationUseCase(stateApplicationRepository);
        applicationUseCase = new ApplicationUseCase(applicationRepository, loanTypeUseCase, stateApplicationUseCase);
    }

    @Test
    void shouldSaveApplicationTest() {
        Application application = Application.builder()
                .documentNumber("57685956")
                .amount(4834323)
                .term(6)
                .loanTypeCode("HIP")
                .active(true)
                .build();

        LoanType loanType = LoanType.builder()
                .code("HIP")
                .active(true)
                .build();

        StateApplication stateApplication = StateApplication.builder()
                .code("PEN")
                .active(true)
                .build();

        when(loanTypeRepository.findLoanTypeByCode(loanType.getCode()))
                .thenReturn(Mono.just(loanType));

        when(stateApplicationRepository.findStateApplicationByCode(stateApplication.getCode()))
                .thenReturn(Mono.just(stateApplication));

        when(applicationRepository.saveApplication(any()))
                .thenAnswer(invocation -> {
                    Application a = invocation.getArgument(0);
                    return Mono.just(a);
                });

        // Act & Assert
        StepVerifier.create(applicationUseCase.saveApplication(application))
                .expectNextMatches(u -> u.getDocumentNumber().equals("57685956"))
                .verifyComplete();

        // Verificar que el usuario se guardó
        ArgumentCaptor<Application> captor = ArgumentCaptor.forClass(Application.class);
        verify(applicationRepository).saveApplication(captor.capture());
        Application applicationSaved = captor.getValue();
        assertEquals("57685956", applicationSaved.getDocumentNumber());
    }

    @Test
    void shouldNotSaveApplicationTest() {
        Application application = Application.builder()
                .documentNumber("57685956")
                .amount(4834323)
                .term(6)
                .loanTypeCode("HIPO")
                .active(true)
                .build();

        LoanType loanType = LoanType.builder()
                .code("HIPO")
                .active(true)
                .build();

        StateApplication stateApplication = StateApplication.builder()
                .code("PEN")
                .active(true)
                .build();

        when(loanTypeRepository.findLoanTypeByCode(loanType.getCode()))
                .thenReturn(Mono.empty());

        when(stateApplicationRepository.findStateApplicationByCode(stateApplication.getCode()))
                .thenReturn(Mono.just(stateApplication));

        StepVerifier.create(applicationUseCase.saveApplication(application))
                .expectError()
                .verify();
    }
}
