package com.VU.PSKProject.Service;

import com.VU.PSKProject.Entity.User;
import com.VU.PSKProject.Repository.UserRepository;
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
    public UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User: " + userName + " not found"));
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(String.valueOf(user.getUserRole()))));
    }
}