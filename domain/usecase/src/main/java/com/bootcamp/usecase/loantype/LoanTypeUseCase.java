package com.bootcamp.usecase.loantype;

import com.bootcamp.model.loantype.LoanType;
import com.bootcamp.model.loantype.gateways.LoanTypeRepository;
import com.bootcamp.usecase.exception.LoanTypeNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import reactor.core.publisher.Mono;

@Log
@RequiredArgsConstructor
public class LoanTypeUseCase {
    private final LoanTypeRepository loanTypeRepository;

    public Mono<LoanType> saveLoanType(LoanType loanType) {
        log.info("Use Case saveLoanType");
        loanType.setActive(true);
        return loanTypeRepository.saveLoanType(loanType);
    }

    public Mono<LoanType> findLoanTypeByCode(String code) {
        log.info("Use Case findLoanTypeByCode");
        return loanTypeRepository.findLoanTypeByCode(code)
                .switchIfEmpty(Mono.error(new LoanTypeNotFoundException(
                        "El código " + code + " no existe en el sistema"
                )));
    }

}
