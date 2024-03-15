package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.Iterator;

@Service
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDetailService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if ( user == null )
            throw new UsernameNotFoundException(username);
        Iterator<Role> iterator = user.getRoles().iterator();
        String[] resultRoles = new String[user.getRoles().size()];
        int i = 0;
        while (iterator.hasNext()) {
            Role element = iterator.next();
            resultRoles[i++] = element.getName();

        }
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
//                .password(bCryptPasswordEncoder.encode(user.getPassword()))
//                .roles("ADMIN", "USER")
//                .authorities(user.getAuthorities().stream().forEach(x-> x.getAuthority()),)
//                .roles(user.getRoles().stream().forEach(x->(x.getName()=="ADMIN") ? "ADMIN": "USER")
                .roles(resultRoles)
                .build();

    }
}
