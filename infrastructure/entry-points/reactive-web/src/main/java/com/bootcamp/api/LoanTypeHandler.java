package com.bootcamp.api;

import com.bootcamp.api.validator.RequestValidator;
import com.bootcamp.model.loantype.LoanType;
import com.bootcamp.usecase.loantype.LoanTypeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class LoanTypeHandler {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private final LoanTypeUseCase loanTypeUseCase;
    private final RequestValidator requestValidator;

    public Mono<ServerResponse> findLoanTypeByCode(ServerRequest serverRequest) {
            return ServerResponse.ok()
                    .contentType(MediaType.TEXT_EVENT_STREAM)
                    .body(loanTypeUseCase.findLoanTypeByCode("PER"), LoanType.class);
        }
}
