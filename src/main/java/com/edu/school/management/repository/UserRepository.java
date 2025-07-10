package com.edu.school.management.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.school.management.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
   

	Optional<UserEntity> findByUsernameAndPassword(String username, String password);
	List<UserEntity> findByRoleRoleId(Long roleId);

}

