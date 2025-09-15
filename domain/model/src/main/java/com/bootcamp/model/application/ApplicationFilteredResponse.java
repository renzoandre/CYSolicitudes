package com.bootcamp.model.application;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ApplicationFilteredResponse {
    String name;
    String email;
    Double baseSalary;
    Double amount;
    int term;
    String stateApplication;
    String loanType;
    Double interestRate;
    Double monthlyAmountApplication;
}
