package com.example.scheduleproject.user.service;

import com.example.scheduleproject.common.exception.CustomException;
import com.example.scheduleproject.common.exception.ExceptionMessage;
import com.example.scheduleproject.user.dto.req.CreateUserRequest;
import com.example.scheduleproject.user.dto.req.LoginRequest;
import com.example.scheduleproject.user.dto.res.GetUserResponse;
import com.example.scheduleproject.user.dto.res.LoginResponse;
import com.example.scheduleproject.user.entity.User;
import com.example.scheduleproject.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;  //암호화

    @Transactional
    public GetUserResponse signup(CreateUserRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new CustomException(ExceptionMessage.DUPLICATE_EMAIL);
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(
              request.getUsername(),
              request.getEmail(),
                encodedPassword
        );

        User savedUser = userRepository.save(user);

        return new GetUserResponse(
                savedUser.getUserId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getCreatedDate(),
                savedUser.getUpdatedDate()
        );
    }

    private void verifyPassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new CustomException(ExceptionMessage.INVALID_PASSWORD);
        }
    }

    @Transactional(readOnly = true)
    public LoginResponse login(@Valid LoginRequest request, HttpSession session) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(ExceptionMessage.INVALID_LOGIN));

        verifyPassword(request.getPassword(), user.getPassword());

        // 세션 저장 로직을 여기서 처리
        session.setAttribute("userId", user.getUserId());
        session.setAttribute("email", user.getEmail());

        return new LoginResponse(user.getUserId(), user.getUsername(), user.getEmail());
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<GetUserResponse> findAll() {
        return userRepository.findAll().stream()
                .map(user -> new GetUserResponse(
                        user.getUserId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getCreatedDate(),
                        user.getUpdatedDate()
                ))
                .toList();
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public GetUserResponse findById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new CustomException(ExceptionMessage.USER_NOT_FOUND));

        return new GetUserResponse(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedDate(),
                user.getUpdatedDate()
        );
    }



    @Transactional
    public void delete(Long userId, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new CustomException(ExceptionMessage.USER_NOT_FOUND));

        verifyPassword(password, user.getPassword());

        userRepository.delete(user);
    }
}
