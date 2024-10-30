package com.luv2code.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = {@JoinColumn (name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn (name = "role_id", referencedColumnName = "id")}
    )
    private List<Role> roles;
}