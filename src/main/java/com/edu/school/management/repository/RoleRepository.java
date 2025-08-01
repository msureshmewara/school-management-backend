package com.edu.school.management.repository;

import com.edu.school.management.entity.RoleEntity;
import com.edu.school.management.projection.RoleSummary;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
	boolean existsByTitle(String title);
    Optional<RoleEntity> findByTitle(String title);
    List<RoleSummary> findAllProjectedBy();

}
