package com.VU.PSKProject.Service.Mapper;

import com.VU.PSKProject.Entity.Role;
import com.VU.PSKProject.Service.Model.RoleDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleMapper {
    @Autowired
    private ModelMapper modelMapper;

    public RoleDTO toDto(Role role){
        return modelMapper.map(role, RoleDTO.class);
    }
}
