package com.aditya.todoApp.Repo;

import com.aditya.todoApp.Model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser , Long> {
    Optional<AppUser> findByUserName(String userName);
}
