package com.example.scheduleproject.user.controller;

import com.example.scheduleproject.user.repository.UserRepository;
import com.example.scheduleproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


}
