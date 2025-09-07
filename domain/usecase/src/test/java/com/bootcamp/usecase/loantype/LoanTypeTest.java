package com.bootcamp.usecase.loantype;

import com.bootcamp.model.loantype.LoanType;
import com.bootcamp.model.loantype.gateways.LoanTypeRepository;
import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

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
    void shouldFindLoanTypeByCodeTest() {
        LoanType loanType = LoanType.builder()
                .code("HIP")
                .active(true)
                .build();

        when(loanTypeRepository.findLoanTypeByCode(loanType.getCode()))
                .thenReturn(Mono.just(loanType));

        StepVerifier.create(loanTypeUseCase.findLoanTypeByCode(loanType.getCode()))
                .expectNextMatches(loanFound -> loanFound.getCode().equals("HIP"))
                .verifyComplete();
    }

    @Test
    void shouldNotFindLoanTypeByCodeTest() {
        LoanType loanType = LoanType.builder()
                .code("ANY")
                .active(true)
                .build();

        when(loanTypeRepository.findLoanTypeByCode(loanType.getCode()))
                .thenReturn(Mono.empty());

        StepVerifier.create(loanTypeUseCase.findLoanTypeByCode(loanType.getCode()))
                .expectError()
                .verify();
    }

    @Test
    void shouldFindLoanTypeByIdTest() {
        LoanType loanType = LoanType.builder()
                .id(UUID.fromString("03608374-1877-4623-af3a-700a0f822900"))
                .active(true)
                .build();

        when(loanTypeRepository.findLoanTypeByCode(loanType.getCode()))
                .thenReturn(Mono.just(loanType));

        StepVerifier.create(loanTypeUseCase.findLoanTypeByCode(loanType.getCode()))
                .expectNextMatches(loanFound -> loanFound.getId().equals(UUID.fromString("03608374-1877-4623-af3a-700a0f822900")))
                .verifyComplete();
    }

    @Test
    void shouldNotFindLoanTypeByIdTest() {
        LoanType loanType = LoanType.builder()
                .id(UUID.fromString("03608374-1877-4623-af3a-700a0f822900"))
                .active(true)
                .build();

        when(loanTypeRepository.findLoanTypeByCode(loanType.getCode()))
                .thenReturn(Mono.empty());

        StepVerifier.create(loanTypeUseCase.findLoanTypeByCode(loanType.getCode()))
                .expectError()
                .verify();
    }

}
