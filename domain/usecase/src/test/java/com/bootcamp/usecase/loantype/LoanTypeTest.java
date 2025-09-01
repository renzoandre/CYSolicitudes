package com.bootcamp.usecase.loantype;

import com.bootcamp.model.application.Application;
import com.bootcamp.model.loantype.LoanType;
import com.bootcamp.model.loantype.gateways.LoanTypeRepository;
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
public class LoanTypeTest {

    private LoanTypeRepository loanTypeRepository;
    private LoanTypeUseCase loanTypeUseCase;

    @BeforeEach
    void setUp() {
        loanTypeRepository = mock(LoanTypeRepository.class);
        loanTypeUseCase = new LoanTypeUseCase(loanTypeRepository);
    }

    @Test
    void saveLoanTypeTest() {
        LoanType loanType = LoanType.builder()
                //.id(UUID.fromString("e6424d99-9bf9-4dea-b554-cffddabe78d0"))
                .code("COM")
                .name("Préstamo Comercial")
                .active(true)
                .build();

        when(loanTypeRepository.saveLoanType(any()))
                .thenAnswer(invocation -> {
                    LoanType a = invocation.getArgument(0);
                    return Mono.just(a);
                });

        StepVerifier.create(loanTypeUseCase.saveLoanType(loanType))
                .expectNextMatches(l -> l.getCode().equals("COM"))
                .verifyComplete();

        ArgumentCaptor<LoanType> captor = ArgumentCaptor.forClass(LoanType.class);
        verify(loanTypeRepository).saveLoanType(captor.capture());
        LoanType loanTypeSaved = captor.getValue();
        assertEquals("COM", loanTypeSaved.getCode());
    }

    @Test
    void findLoanTypeByCodeTest() {
        LoanType loanType = LoanType.builder()
                //.id(UUID.fromString("e6424d99-9bf9-4dea-b554-cffddabe78d0"))
                .code("HIP")
                //.name("Préstamo Comercial")
                .active(true)
                .build();

        when(loanTypeRepository.findLoanTypeByCode(loanType.getCode()))
                .thenReturn(Mono.empty());

        StepVerifier.create(loanTypeUseCase.findLoanTypeByCode(loanType.getCode()))
                .expectNextMatches(l -> l.getCode().equals("HIP"))
                .verifyComplete();
    }

}
