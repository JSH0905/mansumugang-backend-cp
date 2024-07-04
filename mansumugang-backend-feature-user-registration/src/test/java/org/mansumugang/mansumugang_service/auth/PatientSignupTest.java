package org.mansumugang.mansumugang_service.auth;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mansumugang.mansumugang_service.dto.auth.signup.PatientSignupRequestDto;
import org.mansumugang.mansumugang_service.dto.auth.signup.SignUpDto;
import org.mansumugang.mansumugang_service.dto.auth.signup.SignUpResponseDto;
import org.mansumugang.mansumugang_service.repository.UserRepository;
import org.mansumugang.mansumugang_service.service.auth.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@Slf4j
@SpringBootTest
public class PatientSignupTest {


    @Autowired
    private SignUpService signUpService;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("환자 회원가입 테스트")
    @Transactional
    @Rollback(value = true)
    public void signupTest() {

        log.info("회원가입 시작");

        // given
        log.info("signupRequestDto 작성");
        PatientSignupRequestDto patientSignupRequestDto = new PatientSignupRequestDto();
        patientSignupRequestDto.setUsername("test1");
        patientSignupRequestDto.setPassword("qazwsxwjd603905!");
        patientSignupRequestDto.setPasswordCheck("qazwsxwjd603905!");
        patientSignupRequestDto.setName("테스터1");
        patientSignupRequestDto.setBirthdate("2000-09-05");
        patientSignupRequestDto.setTelephone("010-6209-6156");

        log.info("dto 작성완료 = {}", patientSignupRequestDto.getUsername());

        // when
        SignUpDto signUpDto = signUpService.patientSignup(patientSignupRequestDto);

        SignUpResponseDto newUser = SignUpResponseDto.dtoToResponse(signUpDto);

        log.info("db 확인 = {}", userRepository.findByUsername("test1"));

        log.info("newUser.name={}", newUser.getUsername());
        log.info("newUSer.authority={}", newUser.getAuthority());
        log.info("newUSer.userType={}", newUser.getUsertype());

        // then
        

        // 필요한 경우, 여기서 추가 검증 로직을 작성합니다.
    }
}
