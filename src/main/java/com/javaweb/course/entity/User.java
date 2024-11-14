package com.javaweb.course.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.javaweb.course.enums.AuthProvider;
import com.javaweb.course.enums.State;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "user")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    private State state = State.ACTIVE;

    private String sub;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @ToString.Exclude
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    @ToString.Exclude
    private Student student;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<RegistrationCourse> registrationCourses = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    private Date create_at;
    private Date update_at;

    @Lob
    private byte[] image;

}