package com.example.scheduleproject.user.controller;

import com.example.scheduleproject.user.dto.req.CreateUserRequest;
import com.example.scheduleproject.user.dto.req.LoginRequest;
import com.example.scheduleproject.user.dto.res.GetUserResponse;
import com.example.scheduleproject.user.dto.res.LoginResponse;
import com.example.scheduleproject.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<GetUserResponse> signup(
            @Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signup(request));
    }

    //로그인(세션)
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletRequest httpRequest){
        LoginResponse response= userService.login(request);

        HttpSession session = httpRequest.getSession();
        session.setAttribute("userId", response.getUserId());
        session.setAttribute("email", response.getEmail());

        return ResponseEntity.ok(response);
    }


}
