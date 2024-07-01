package org.mansumugang.mansumugang_service.dto.auth.signup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SignUpResponseDto {
    private String username;
    private String authority;

    public static SignUpResponseDto dtoToResponse(SignUpDto signupDto){
        return SignUpResponseDto.builder()
                .username(signupDto.getUsername())
                .authority(signupDto.getAuthority())
                .build();
    }
}
