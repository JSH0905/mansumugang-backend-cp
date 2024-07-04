package org.mansumugang.mansumugang_service.auth;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mansumugang.mansumugang_service.dto.auth.signup.ProtectorSignUpRequestDto;
import org.mansumugang.mansumugang_service.dto.auth.signup.SignUpDto;
import org.mansumugang.mansumugang_service.dto.auth.signup.SignUpResponseDto;
import org.mansumugang.mansumugang_service.repository.UserRepository;
import org.mansumugang.mansumugang_service.service.auth.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@Slf4j
@SpringBootTest
public class ProtectorSignupTest {


    @Autowired
    private SignUpService signUpService;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("보호자 회원가입 테스트")
    @Transactional
    @Rollback(value = true)
    public void signupTest() {

        log.info("회원가입 시작");

        // given
        log.info("ProtectorSignupRequestDto 작성 시작");
        ProtectorSignUpRequestDto protectorSignUpRequestDto = new ProtectorSignUpRequestDto();
        protectorSignUpRequestDto.setUsername("test2");
        protectorSignUpRequestDto.setPassword("qazwsxwjd603905!");
        protectorSignUpRequestDto.setPasswordCheck("qazwsxwjd603905!");
        protectorSignUpRequestDto.setName("테스터2");
        protectorSignUpRequestDto.setBirthdate("2000-09-05");
        protectorSignUpRequestDto.setNickname("kkOma_fan");
        protectorSignUpRequestDto.setEmail("2000tjdgns@naver.com");

        log.info("dto 작성완료 = {}", protectorSignUpRequestDto.getUsername());

        // when
        SignUpDto signUpDto = signUpService.protectorSignup(protectorSignUpRequestDto);

        SignUpResponseDto newUser = SignUpResponseDto.dtoToResponse(signUpDto);

        log.info("db 확인 = {}", userRepository.findByUsername("test2"));

        log.info("newUser.name={}", newUser.getUsername());
        log.info("newUSer.authority={}", newUser.getAuthority());
        log.info("newUSer.userType={}", newUser.getUsertype());

        // then


        // 필요한 경우, 여기서 추가 검증 로직을 작성합니다.
    }
}
