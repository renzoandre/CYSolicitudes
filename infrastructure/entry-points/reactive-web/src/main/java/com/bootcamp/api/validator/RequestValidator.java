package com.bootcamp.api.validator;

import com.bootcamp.api.exception.ValidationDtoException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class RequestValidator {
    private final Validator validator;

    public <T> Mono<T> validate(T dto) {
        Set<ConstraintViolation<T>> violations = validator.validate(dto);

        if (!violations.isEmpty()) {
            String errorMessage = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .findFirst()
                    .orElse("Datos inválidos");

            return Mono.error(new ValidationDtoException(errorMessage));
        }

        return Mono.just(dto);
    }
}
