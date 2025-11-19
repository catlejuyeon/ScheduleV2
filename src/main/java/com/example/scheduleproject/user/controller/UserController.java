package com.example.scheduleproject.user.controller;

import com.example.scheduleproject.user.dto.req.CreateUserRequest;
import com.example.scheduleproject.user.dto.req.DeleteUserRequest;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            HttpSession session) {
        return ResponseEntity.ok(userService.login(request, session));
    }

    //유저 전체 조회
    @GetMapping
    public ResponseEntity<List<GetUserResponse>> getAllUser(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    //유저 단건 조회
    @GetMapping("/{userId}")
    public ResponseEntity<GetUserResponse> getOneUser(@PathVariable Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(userId));
    }

    //유저 삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long userId,
            @RequestParam String password) {
        userService.delete(userId,password);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
