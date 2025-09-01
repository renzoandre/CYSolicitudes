package com.bootcamp.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.NumberFormat;

public record CreateApplicationDto (
        @NotNull(message = "Debe ingresar el número de documento")
        @NotBlank(message = "Debe ingresar el número de documento")
        @NumberFormat(style = NumberFormat.Style.NUMBER)
        String documentNumber,
        @NotNull(message = "Debe ingresar el monto")
        double amount,
        @NotNull(message = "Debe ingresar el plazo")
        int term,
        @NotNull(message = "Debe ingresar el tipo de prestamo")
        @NotBlank(message = "Debe ingresar el tipo de prestamo")
        String loanType
) {}
