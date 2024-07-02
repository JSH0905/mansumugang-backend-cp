package org.mansumugang.mansumugang_service.service.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mansumugang.mansumugang_service.constant.ErrorType;
import org.mansumugang.mansumugang_service.domain.user.User;
import org.mansumugang.mansumugang_service.dto.auth.signup.*;
import org.mansumugang.mansumugang_service.exception.UserErrorException;
import org.mansumugang.mansumugang_service.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignUpService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public SignUpDto patientSignup(PatientSignupRequestDto patientSignupRequestDto){
        if(userRepository.findByUsername(patientSignupRequestDto.getUsername()).isPresent()){ // 중복된 아이디가 있을 경우 예외
            throw new UserErrorException(ErrorType.DuplicatedUsernameError);
        }

        // 비밀번호, 비밀번호 재입력이 동일한지 검사
        checkPasswordIsEqual(patientSignupRequestDto.getPassword(), patientSignupRequestDto.getPasswordCheck());

        // 성공로직(아이디,닉네임 중복X 및 비밀번호(1차입력,2차입력)동일시)
        User newPatientUser = userRepository.save(User.patientRequestDtoToUser(patientSignupRequestDto, passwordEncoder));
        return SignUpDto.fromEntity(newPatientUser);

    }
    @Transactional
    public SignUpDto protectorSignup(ProtectorSignUpRequestDto protectorSignUpRequestDto){
        if(userRepository.findByUsername(protectorSignUpRequestDto.getUsername()).isPresent()){ // 중복된 아이디가 있을 경우 예외
            throw new UserErrorException(ErrorType.DuplicatedUsernameError);
        }

        if(userRepository.findByNickname(protectorSignUpRequestDto.getNickname()).isPresent()){
            throw new UserErrorException(ErrorType.DuplicatedNicknameError);
        }

        // 비밀번호, 비밀번호 재입력이 동일한지 검사
        checkPasswordIsEqual(protectorSignUpRequestDto.getPassword(), protectorSignUpRequestDto.getPasswordCheck());

        // 성공로직(아이디,닉네임 중복X 및 비밀번호(1차입력,2차입력)동일시)
        User newProtectorUser = userRepository.save(User.protectorRequestDtoToUser(protectorSignUpRequestDto, passwordEncoder));
        return SignUpDto.fromEntity(newProtectorUser);

    }

    public void checkPasswordIsEqual(String password, String passwordCheck){
        if(!password.equals(passwordCheck)){
            throw new UserErrorException(ErrorType.NotEqualPasswordAndPasswordCheck);//에러코드
        }
    }

    public void checkUsernameDuplication(UsernameDuplicationCheckDto usernameDuplicationCheckDto){
        if(userRepository.findByNickname(usernameDuplicationCheckDto.getUsername()).isPresent()){
            throw new UserErrorException(ErrorType.DuplicatedUsernameError);
        }
    }

    public void checkNicknameDuplication(NicknameDuplicationCheckDto nicknameDuplicationCheckDto){
        if(userRepository.findByNickname(nicknameDuplicationCheckDto.getNickname()).isPresent()){
            throw new UserErrorException(ErrorType.DuplicatedUsernameError);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
