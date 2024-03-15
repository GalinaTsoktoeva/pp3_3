package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
@Entity
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "roles")
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
    public Role(){};
    public Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
