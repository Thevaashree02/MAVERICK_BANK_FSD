package com.maverick.service;

import java.util.List;

import com.maverick.entity.User;
import com.maverick.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("Invalid Credentials");
        SimpleGrantedAuthority sga = new SimpleGrantedAuthority(user.getRole());
        List<GrantedAuthority> list = List.of(sga);
        org.springframework.security.core.userdetails.User springUser = new
                org.springframework.security.core.userdetails.User
                (
                        user.getUsername(),
                        user.getPassword(),
                        list);
        return springUser;
    }

}