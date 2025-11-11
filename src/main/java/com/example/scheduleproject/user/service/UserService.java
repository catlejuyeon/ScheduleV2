package com.example.scheduleproject.user.service;

import com.example.scheduleproject.user.dto.req.CreateUserRequest;
import com.example.scheduleproject.user.dto.res.GetUserResponse;
import com.example.scheduleproject.user.entity.User;
import com.example.scheduleproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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
}
