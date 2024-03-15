package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }
    public User findById(Long id){
        return userRepository.getOne(id);
    }

    public User findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    public User saveUser(User user){
        Optional<Role> role = roleRepository.findById(2L);
        if (role.isPresent()) {
            user.setActive(true);
            user.setRoles(Collections.singleton(role.get()));
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }
    public List<User> findAll(){
        return userRepository.findAll();
    }
    public void deleteById(Long id){
        userRepository.deleteById(id);
    }
}
