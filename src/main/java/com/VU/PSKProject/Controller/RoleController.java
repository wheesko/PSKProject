package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.Role;
import com.VU.PSKProject.Service.Model.RoleDTO;
import com.VU.PSKProject.Service.RoleService;
import com.VU.PSKProject.Utils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/getAll")
    public ResponseEntity<List<RoleDTO>> getRoles() {
        List<RoleDTO> roleDTOS = roleService.getAllRoles();
        return ResponseEntity.ok(roleDTOS);
    }

    @GetMapping("/get/{id}")
    public Optional<Role> getRole(@PathVariable Long id) {
        return roleService.getRole(id);
    }

    @PostMapping("/create")
    public void createRole(@RequestBody RoleDTO roleDto) {
        Role role = new Role();
        PropertyUtils.customCopyProperties(roleDto, role);
        roleService.createRole(role);
    }

    @PutMapping("/update/{id}")
    public void updateRole(@RequestBody RoleDTO roleDto, @PathVariable Long id){
        Role role = new Role();
        PropertyUtils.customCopyProperties(roleDto, role);
        roleService.updateRole(id, role);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteRole(@PathVariable Long id){
        roleService.deleteRole(id);
    }
}
