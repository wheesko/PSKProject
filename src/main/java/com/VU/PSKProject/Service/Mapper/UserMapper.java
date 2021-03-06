package com.VU.PSKProject.Service.Mapper;

import com.VU.PSKProject.Entity.User;
import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Service.Model.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    @Autowired
    private ModelMapper modelMapper;

    public UserDTO toDTO(User user) {
        UserDTO dto = modelMapper.map(user, UserDTO.class);
        dto.setUserRole(user.getUserAuthority() == null ? null : user.getUserAuthority().toString());
        return dto;
    }

    public User fromDTO(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
