package org.mansumugang.mansumugang_service.domain.user;

import jakarta.persistence.*;
import lombok.*;
import org.mansumugang.mansumugang_service.dto.auth.signup.PatientSignupRequestDto;
import org.mansumugang.mansumugang_service.dto.auth.signup.ProtectorSignUpRequestDto;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username; // 유저 id

    private String password; // 비밀번호

    private String name; // 이름

    private String birthdate; // 사용자 생년월일

    private String telephone; // 사용자 전화번호

    private String nickname; // 사용자 닉네임

    private String email; // 사용자 이메일

    private String usertype; // 환자, 보호자 구분

    private String authority;

    @CreatedDate
    private LocalDateTime createdAt;

    public User(
            String username,
            String password,
            String authority
    ) {
        this.username = username;
        this.password = password;
        this.authority = authority;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton((GrantedAuthority) () -> authority);
    }


    public static User patientRequestDtoToUser( // 생년월일 추가필요
            PatientSignupRequestDto patientSignupRequestDto,
            PasswordEncoder passwordEncoder
    ) {
        return User.builder()
                .username(patientSignupRequestDto.getUsername())
                .password(passwordEncoder.encode(patientSignupRequestDto.getPassword()))
                .name(patientSignupRequestDto.getName())
                .birthdate(patientSignupRequestDto.getBirthdate())
                .usertype(patientSignupRequestDto.getUsertype())
                .authority("ROLE_USER")
                .build();
    }

    public static User protectorRequestDtoToUser( // 생년월일 추가 필요
            ProtectorSignUpRequestDto protectorSignUpRequestDto, PasswordEncoder passwordEncoder
    ) {

        return User.builder()
                .username(protectorSignUpRequestDto.getUsername())
                .password(passwordEncoder.encode(protectorSignUpRequestDto.getPassword()))
                .name(protectorSignUpRequestDto.getName())
                .birthdate(protectorSignUpRequestDto.getBirthdate())
                .email(protectorSignUpRequestDto.getEmail())
                .nickname(protectorSignUpRequestDto.getNickname())
                .usertype(protectorSignUpRequestDto.getUsertype())
                .authority("ROLE_USER")
                .build();
    }


    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }


}
