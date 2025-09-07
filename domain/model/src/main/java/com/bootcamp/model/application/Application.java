package com.bootcamp.model.application;


import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Application {
    private UUID id;
    private String documentNumber;
    private double amount;
    private int term;
    private UUID loanTypeId;
    private String loanTypeCode;
    private UUID stateId;
    private String stateCode;
    private boolean active;
}
