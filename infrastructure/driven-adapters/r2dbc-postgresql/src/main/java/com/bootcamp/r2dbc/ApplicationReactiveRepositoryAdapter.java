package com.bootcamp.r2dbc;

import com.bootcamp.model.application.Application;
import com.bootcamp.model.application.gateways.ApplicationRepository;
import com.bootcamp.r2dbc.entity.ApplicationEntity;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class ApplicationReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        Application,
        ApplicationEntity,
        String,
        ApplicationReactiveRepository
> implements ApplicationRepository {
    private static final Logger log = LoggerFactory.getLogger(ApplicationReactiveRepositoryAdapter.class);

    public ApplicationReactiveRepositoryAdapter(ApplicationReactiveRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, applicationEntity -> mapper.map(applicationEntity, Application.class));
    }

    @Transactional
    @Override
    public Mono<Application> saveApplication(Application application) {
        log.info("ApplicationReactiveRepositoryAdapter saveApplication: {}", application);
        application.setActive(true);
        return super.save(application)
                .onErrorMap(DataIntegrityViolationException.class,
                        ex -> new DataValidationException("Integridad de datos inválidos"))
                .onErrorMap(TransientDataAccessResourceException.class,
                        ex -> new DatabaseUnavailableException("Base de datos no disponible"))
                .onErrorMap(Exception.class,
                        ex -> {
                            log.error("💥 Error inesperado al guardar solicitud", ex);
                            return new RuntimeException("Error inesperado al guardar solicitud", ex);
                        });
    }

    @Transactional
    @Override
    public Flux<Application> findApplicationsFilter(String documentNumber, UUID loanTypeId, UUID statusApplicationId) {
        log.info("ApplicationReactiveRepositoryAdapter findApplicationsFilter");

        return findAll()
                .filter(application -> application.getLoanTypeId().equals(loanTypeId) ||
                        application.getStateId().equals(statusApplicationId) || application.getDocumentNumber().equals(documentNumber))
                .doOnNext(app -> log.info("Application encontrado: {}", app))
                .switchIfEmpty(Flux.empty())
                .doOnComplete(() -> log.info("2 No se encontraron más applications para documentNumber={}", documentNumber))
                .onErrorMap(TransientDataAccessResourceException.class,
                        ex -> new DatabaseUnavailableException("Base de datos no disponible"))
                .onErrorMap(Exception.class,
                        ex -> new RuntimeException("Error inesperado al econtrar solicitudes", ex));
    }

}
