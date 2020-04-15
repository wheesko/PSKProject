package com.VU.PSKProject.Service;

import com.VU.PSKProject.Entity.Role;
import com.VU.PSKProject.Entity.Topic;
import com.VU.PSKProject.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() { return roleRepository.findAll();
    }

    public void createRole(Role role) { roleRepository.save(role);
    }

    public void updateRole(Long id, Role role) {
        if (roleRepository.findById(id) != null) roleRepository.save(role);
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    public Optional<Role> getRole(Long id) {
        return roleRepository.findById(id);
    }
}
