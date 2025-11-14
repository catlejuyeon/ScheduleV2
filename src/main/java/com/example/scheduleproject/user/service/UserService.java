package com.example.scheduleproject.user.service;

import com.example.scheduleproject.user.dto.req.CreateUserRequest;
import com.example.scheduleproject.user.dto.req.LoginRequest;
import com.example.scheduleproject.user.dto.res.GetUserResponse;
import com.example.scheduleproject.user.dto.res.LoginResponse;
import com.example.scheduleproject.user.entity.User;
import com.example.scheduleproject.user.repository.UserRepository;
import jakarta.persistence.Table;
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
            throw new IllegalArgumentException("중복된 이메일입니다.");
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

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public LoginResponse login(@Valid LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다."));

        // 평문 입력 vs 암호화된 DB 비밀번호 비교
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        return new LoginResponse(
            user.getUserId(),
            user.getUsername(),
            user.getEmail()
        );
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<GetUserResponse> findAll() {
        List<User> users= (List<User>) userRepository.findAll();

        return users.stream()
                .map(user-> new GetUserResponse(
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
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 유저입니다."));

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
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 유저입니다."));

        // 평문 입력 vs 암호화된 DB 비밀번호 비교
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        userRepository.delete(user);
    }
}
