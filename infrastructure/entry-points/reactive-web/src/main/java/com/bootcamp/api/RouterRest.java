package com.bootcamp.api;

import com.bootcamp.api.config.ApplicationPathsConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class RouterRest {
    private final ApplicationPathsConfig applicationPathsConfig;
    private final Handler applicatinHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return route(POST(applicationPathsConfig.getApplication()), applicatinHandler::saveApplication);
    }
}
