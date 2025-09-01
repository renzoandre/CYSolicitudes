package com.bootcamp.model.loantype.gateways;

import com.bootcamp.model.loantype.LoanType;
import reactor.core.publisher.Mono;

public interface LoanTypeRepository {
    Mono<LoanType> saveLoanType(LoanType loanType);
    Mono<LoanType> findLoanTypeByCode(String code);
}
