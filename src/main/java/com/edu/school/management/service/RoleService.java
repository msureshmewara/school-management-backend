package com.edu.school.management.service;

import com.edu.school.management.entity.RoleEntity;
import com.edu.school.management.projection.RoleSummary;
import com.edu.school.management.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleEntity createRole(RoleEntity role) {
        return roleRepository.save(role);
    }

    public List<RoleEntity> getAllRoles() {
        return roleRepository.findAll();
    }

    public List<RoleSummary> getAllRoleSummaries() {
        return roleRepository.findAllProjectedBy();
    }
    
    public Optional<RoleEntity> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}
