package com.bootcamp.r2dbc;

import com.bootcamp.r2dbc.entity.StateApplicationEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface StateApplicationReactiveRepository extends ReactiveCrudRepository<StateApplicationEntity, String>, ReactiveQueryByExampleExecutor<StateApplicationEntity> {

}
