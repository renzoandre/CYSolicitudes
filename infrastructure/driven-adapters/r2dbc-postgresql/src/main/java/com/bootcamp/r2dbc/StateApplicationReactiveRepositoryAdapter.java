package com.bootcamp.r2dbc;

import com.bootcamp.model.stateapplication.StateApplication;
import com.bootcamp.model.stateapplication.gateways.StateApplicationRepository;
import com.bootcamp.r2dbc.entity.StateApplicationEntity;
import com.bootcamp.r2dbc.exception.DatabaseUnavailableException;
import com.bootcamp.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class StateApplicationReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        StateApplication,
        StateApplicationEntity,
        String,
        StateApplicationReactiveRepository
> implements StateApplicationRepository {
    private static final Logger log = LoggerFactory.getLogger(StateApplicationReactiveRepositoryAdapter.class);

    public StateApplicationReactiveRepositoryAdapter(StateApplicationReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, stateApplicationEntity -> mapper.map(stateApplicationEntity, StateApplication.class));
    }

    @Transactional
    @Override
    public Mono<StateApplication> findStateApplicationByCode(String code) {
        log.info("StateApplicationReactiveRepositoryAdapter findStateApplicationByCode: {}", code);
        StateApplication stateApplication = new StateApplication();
        stateApplication.setCode(code);
        stateApplication.setActive(true);
        return findByExample(stateApplication)
                .next()
                .switchIfEmpty(Mono.empty())
                .onErrorMap(TransientDataAccessResourceException.class,
                        ex -> new DatabaseUnavailableException("Base de datos no disponible"))
                .onErrorMap(Exception.class,
                        ex -> new RuntimeException("Error inesperado al buscar tipo de prestamo", ex));
    }

    @Transactional
    @Override
    public Mono<StateApplication> findStateApplicationById(UUID id) {
        log.info("StateApplicationReactiveRepositoryAdapter findStateApplicationById: {}", id);

        return findById(id.toString())
                .switchIfEmpty(Mono.empty())
                .onErrorMap(TransientDataAccessResourceException.class,
                        ex -> new DatabaseUnavailableException("Base de datos no disponible"))
                .onErrorMap(Exception.class,
                        ex -> new RuntimeException("Error inesperado al buscar tipo de prestamo", ex));
    }
}
