package com.javaweb.course.entity;

import com.javaweb.course.enums.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username",nullable = false, length = 50, unique = true)
    private String username;

    @Column(name = "password",length = 64)
    private String password;

    private boolean status;

    private String sub;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST},mappedBy = "user")
    private Student student;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    private Date create_at;
    private Date update_at;


}