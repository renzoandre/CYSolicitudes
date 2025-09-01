package com.bootcamp.api.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponseHelper<T> {
    private boolean success;
    private String message;
    private T data;
}