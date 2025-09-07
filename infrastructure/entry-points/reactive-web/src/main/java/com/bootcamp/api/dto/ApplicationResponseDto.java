package com.bootcamp.api.dto;

import java.util.UUID;

public record ApplicationResponseDto(
        UUID id,
        String documentNumber,
        double amount,
        int term,
        boolean active
) {}
