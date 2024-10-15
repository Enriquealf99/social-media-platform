package com.socialmedia.DTO;

import lombok.*;



@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
}
