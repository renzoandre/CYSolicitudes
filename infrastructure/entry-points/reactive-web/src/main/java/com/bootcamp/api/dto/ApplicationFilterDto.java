package com.bootcamp.api.dto;

public record ApplicationFilterDto(
    String documentNumber,
    String email,
    String stateApplication,
    String loanType
) {}
