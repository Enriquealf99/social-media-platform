package com.socialmedia.DTO;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
    private String email;
    private String password;
}
