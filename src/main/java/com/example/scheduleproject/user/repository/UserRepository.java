package com.example.scheduleproject.user.repository;

import com.example.scheduleproject.user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String username);

    boolean existsByEmail(String email);
}
