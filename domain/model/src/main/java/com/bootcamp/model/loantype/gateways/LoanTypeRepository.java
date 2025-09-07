package com.bootcamp.model.loantype.gateways;

import com.bootcamp.model.loantype.LoanType;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface LoanTypeRepository {
    Mono<LoanType> saveLoanType(LoanType loanType);
    Mono<LoanType> findLoanTypeByCode(String code);
    Mono<LoanType> findLoanTypeById(UUID id);
}
