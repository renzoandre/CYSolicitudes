package com.bootcamp.consumer;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LoginRequest {
    private String account;
    private String password;
}
