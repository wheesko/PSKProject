package com.VU.PSKProject.Service;

import com.VU.PSKProject.Entity.Role;
import com.VU.PSKProject.Entity.RoleGoal;
import com.VU.PSKProject.Repository.RoleRepository;
import com.VU.PSKProject.Service.Mapper.RoleGoalMapper;
import com.VU.PSKProject.Service.Mapper.RoleMapper;
import com.VU.PSKProject.Service.Model.RoleDTO;
import com.VU.PSKProject.Service.Model.RoleGoalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleGoalMapper roleGoalMapper;

    public List<RoleDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        List<RoleDTO> roleDTOS = new ArrayList<>();
        for (Role r : roles) {
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setId(r.getId());
            roleDTO.setName(r.getName());
            List<RoleGoalDTO> roleGoals = new ArrayList<>();
            for (RoleGoal g : r.getRoleGoals()) {
                RoleGoalDTO roleGoalDTO = new RoleGoalDTO();
                roleGoalDTO = roleGoalMapper.toDto(g);
            }
            roleDTO.setRoleGoals(roleGoals);
            roleDTOS.add(roleDTO);
        }
        return roleDTOS;
    }

    public void createRole(Role role) {
        roleRepository.save(role);
    }

    public void updateRole(Long id, Role role) {
        if (roleRepository.findById(id).isPresent()) {
            role.setId(id);
            roleRepository.save(role);
        }
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    public Role findOrCreateRole(String roleName) {
        return roleRepository.findByName(roleName).orElseGet(() -> {
            Role createdRole = new Role();
            createdRole.setName(roleName);
            roleRepository.save(createdRole);
            return createdRole;
        });
    }

    public Optional<Role> getRole(Long id) {
        return roleRepository.findById(id);
    }
}
