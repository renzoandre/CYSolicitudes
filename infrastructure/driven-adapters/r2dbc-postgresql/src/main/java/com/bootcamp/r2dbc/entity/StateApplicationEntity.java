package com.bootcamp.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("cat_state_application")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StateApplicationEntity {
    @Id
    @Column("id")
    private UUID id;
    private String code;
    private String name;
    private boolean active;
}
