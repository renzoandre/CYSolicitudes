package com.bootcamp.r2dbc;

import com.bootcamp.r2dbc.entity.ApplicationEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ApplicationReactiveRepository extends ReactiveCrudRepository<ApplicationEntity, String>, ReactiveQueryByExampleExecutor<ApplicationEntity> {

}
