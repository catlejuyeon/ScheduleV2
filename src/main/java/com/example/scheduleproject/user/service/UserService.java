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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public GetUserResponse signup(CreateUserRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new IllegalArgumentException("중복된 이메일입니다.");
        }

        User user = new User(
              request.getUsername(),
              request.getEmail(),
              request.getPassword()
        );

        User savedUser = userRepository.save(user);

        return new GetUserResponse(
                savedUser.getUserid(),
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

        if(!user.getPassword().equals(request.getPassword())){
            throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        return new LoginResponse(
            user.getUserid(),
            user.getUsername(),
            user.getEmail()
        );
    }
}
