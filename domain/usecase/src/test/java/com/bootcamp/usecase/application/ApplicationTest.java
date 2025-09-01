package com.bootcamp.usecase.application;

import com.bootcamp.model.application.Application;
import com.bootcamp.model.application.gateways.ApplicationRepository;
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

    @BeforeEach
    void setUp() {
        applicationRepository = mock(ApplicationRepository.class);
        applicationUseCase = new ApplicationUseCase(applicationRepository);
    }

    @Test
    void saveApplicationTest() {
        Application application = Application.builder()
                //.id(UUID.fromString("e6424d99-9bf9-4dea-b554-cffddabe78d0"))
                .documentNumber("57685956")
                .amount(4834323)
                .term(6)
                .loanType("HIPOTECARIO")
                .active(false)
                .build();

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

}
