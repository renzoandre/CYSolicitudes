package com.bootcamp.model.application;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ApplicationFilter {
    String documentNumber;
    String stateApplication;
    String loanType;
}
