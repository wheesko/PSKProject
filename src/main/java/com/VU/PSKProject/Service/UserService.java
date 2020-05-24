package com.VU.PSKProject.Service;

import com.VU.PSKProject.Entity.User;
import com.VU.PSKProject.Entity.UserAuthority;
import com.VU.PSKProject.Repository.UserRepository;
import com.VU.PSKProject.Service.Mapper.UserMapper;
import com.VU.PSKProject.Service.Model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;

@Service
@RequestMapping("/api")
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    public UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user){
        userRepository.save(user);
    }

    public User createUserFromEmail(String email){
        String temporaryPassword = "$2a$04$KNLUwOWHVQZVpXyMBNc7JOzbLiBjb9Tk9bP7KNcPI12ICuvzXQQKG"; // encoded "admin" string
        User u = new User(email, temporaryPassword, UserAuthority.WORKER);
        createUser(u);
        return u;
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User: " + userName + " not found"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(String.valueOf(user.getUserAuthority()))));
    }

    public UserDTO getUserByEmail(String email) {
       return userMapper.toDTO(userRepository.findByEmail(email).orElse(new User()));
    }
}