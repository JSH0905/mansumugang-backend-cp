package org.mansumugang.mansumugang_service.controller.user;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mansumugang.mansumugang_service.dto.auth.signup.*;
import org.mansumugang.mansumugang_service.service.auth.SignUpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final SignUpService signUpService;

    @PostMapping("/signup/patient") // 환자 회원가입
    public ResponseEntity<SignUpResponseDto> patientSignup(
            @Valid @RequestBody PatientSignupRequestDto patientSignupRequestDto
    ){
        log.info("회원가입 시작");
        SignUpDto signUpDto = signUpService.patientSignup(patientSignupRequestDto);

        return new ResponseEntity<>(SignUpResponseDto.dtoToResponse(signUpDto), HttpStatus.CREATED);

    }

    @PostMapping("/signup/protector") // 보호자 회원가입
    public ResponseEntity<SignUpResponseDto> protectorSignup(
            @Valid @RequestBody ProtectorSignUpRequestDto protectorSignUpRequestDto
            ){
        SignUpDto signUpDto = signUpService.protectorSignup(protectorSignUpRequestDto);
        return new ResponseEntity<>(SignUpResponseDto.dtoToResponse(signUpDto), HttpStatus.CREATED);
    }

    @PostMapping("/check/username") // 유저 id 중복체크 버튼
    public ResponseEntity<UsernameDuplicationCheckResponseDto> checkUsernameDuplication(
            @Valid @RequestBody UsernameDuplicationCheckDto usernameDuplicationCheckDto
    ){

        // 아이디 중복 확인
        signUpService.checkUsernameDuplication(usernameDuplicationCheckDto);

        // 성공 로직(responeDto로 변환 및 반환)
        return new ResponseEntity<>(new UsernameDuplicationCheckResponseDto(), HttpStatus.OK);

    }

    @PostMapping("/check/nickname") // 유저 닉네임 중복확인 버튼
    public ResponseEntity<NicknameDuplicationCheckResponseDto> checkNicknameDuplication(
            @Valid @RequestBody NicknameDuplicationCheckDto nicknameDuplicationCheckDto
    ){

        // 닉네임 중복 확인
        signUpService.checkNicknameDuplication(nicknameDuplicationCheckDto);

        // 성공 로직
        return new ResponseEntity<>(new NicknameDuplicationCheckResponseDto(), HttpStatus.OK);
    }

}
