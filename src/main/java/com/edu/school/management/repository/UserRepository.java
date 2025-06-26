package com.edu.school.management.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.school.management.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
   

	Optional<UserEntity> findByUsernameAndPassword(String username, String password);
}

