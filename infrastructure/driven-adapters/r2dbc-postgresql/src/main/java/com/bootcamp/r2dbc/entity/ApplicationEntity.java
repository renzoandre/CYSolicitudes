package com.bootcamp.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Table("applications")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ApplicationEntity {
    @Id
    @Column("id")
    private UUID id;
    @Column("document_number")
    private String documentNumber;
    private Double amount;
    private int term;
    @Column("loan_type")
    private String loanType;
    private String state;
    private boolean active;
}
