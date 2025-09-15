package com.bootcamp.api.dto;

public record ApplicationFilteredResponseDto(
        String name,
        String email,
        Double baseSalary,
        Double amount,
        int term,
        String stateApplication,
        String loanType,
        Double interestRate,
        Double monthlyAmountApplication
) {}


