package com.bootcamp.model.loantype;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LoanType {
    private UUID id;
    private String code;
    private String name;
    private boolean active;
}
