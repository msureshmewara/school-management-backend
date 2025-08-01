package com.edu.school.management.configs;

import com.edu.school.management.entity.RoleEntity;
import com.edu.school.management.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DefaultRoleInitializer {

    private final RoleRepository roleRepository;

    @PostConstruct
    public void init() {
        // Define default roles
        List<String> defaultRoles = List.of("Super Admin","Admin", "Teacher", "Student");

        for (String title : defaultRoles) {
            boolean exists = roleRepository.existsByTitle(title);
            if (!exists) {
                RoleEntity role = RoleEntity.builder()
                        .title(title)
                        .build();
                roleRepository.save(role);
            }
        }
    }
}
