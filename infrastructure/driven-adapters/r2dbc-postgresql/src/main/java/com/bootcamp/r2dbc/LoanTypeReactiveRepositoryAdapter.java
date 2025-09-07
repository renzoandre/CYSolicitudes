package com.bootcamp.r2dbc;

import com.bootcamp.model.loantype.LoanType;
import com.bootcamp.model.loantype.gateways.LoanTypeRepository;
import com.bootcamp.r2dbc.entity.LoanTypeEntity;
import com.bootcamp.r2dbc.exception.DataValidationException;
import com.bootcamp.r2dbc.exception.DatabaseUnavailableException;
import com.bootcamp.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class LoanTypeReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        LoanType,
        LoanTypeEntity,
        String,
        LoanTypeReactiveRepository
> implements LoanTypeRepository {
    private static final Logger log = LoggerFactory.getLogger(LoanTypeReactiveRepositoryAdapter.class);

    public LoanTypeReactiveRepositoryAdapter(LoanTypeReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, loanTypeEntity -> mapper.map(loanTypeEntity, LoanType.class));
    }

    @Transactional
    @Override
    public Mono<LoanType> saveLoanType(LoanType loanType) {
        log.info("LoanTypeReactiveRepositoryAdapter saveLoanType {}", loanType);
        return super.save(loanType)
                .onErrorMap(DataIntegrityViolationException.class,
                        ex -> new DataValidationException("Integridad de datos inválidos"))
                .onErrorMap(TransientDataAccessResourceException.class,
                        ex -> new DatabaseUnavailableException("Base de datos no disponible"))
                .onErrorMap(Exception.class,
                        ex -> new RuntimeException("Error inesperado al guardar tipo de prestamo", ex));
    }

    @Transactional
    @Override
    public Mono<LoanType> findLoanTypeByCode(String code) {
        log.info("UserReactiveRepositoryAdapter findLoanTypeByCode: {}", code);
        LoanType loanType = new LoanType();
        loanType.setCode(code);
        loanType.setActive(true);
        return findByExample(loanType)
                .next()
                .switchIfEmpty(Mono.empty())
                .onErrorMap(TransientDataAccessResourceException.class,
                        ex -> new DatabaseUnavailableException("Base de datos no disponible"))
                .onErrorMap(Exception.class,
                        ex -> new RuntimeException("Error inesperado al buscar tipo de prestamo", ex));
    }

    @Transactional
    @Override
    public Mono<LoanType> findLoanTypeById(UUID id) {
        log.info("UserReactiveRepositoryAdapter findLoanTypeByid: {}", id);

        return findById(id.toString())
                .switchIfEmpty(Mono.empty())
                .onErrorMap(TransientDataAccessResourceException.class,
                        ex -> new DatabaseUnavailableException("Base de datos no disponible"))
                .onErrorMap(Exception.class,
                        ex -> new RuntimeException("Error inesperado al buscar tipo de prestamo", ex));
    }

}
