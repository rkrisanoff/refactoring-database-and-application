package com.example.kurs.repo;

import com.example.kurs.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepo extends CrudRepository<User, Long> {
    User findByUsername(String username);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
}
