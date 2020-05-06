package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.Role;
import com.VU.PSKProject.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @GetMapping
    public List<Role> getRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("{id}")
    public Optional<Role> getRole(@PathVariable Long id) {
        return roleService.getRole(id);
    }

    @PostMapping
    public void createRole(@RequestBody Role role) {
        roleService.createRole(role);
    }
    @PutMapping("{id}")
    public void updateRole(@RequestBody Role role, @PathVariable Long id){ roleService.updateRole(id, role);
    }
    @DeleteMapping("{id}")
    public void deleteRole(@PathVariable Long id){
        roleService.deleteRole(id);
    }
}
