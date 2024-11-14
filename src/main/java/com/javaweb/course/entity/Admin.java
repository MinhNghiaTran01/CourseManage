package com.javaweb.course.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "admin")
public class Admin {

    @Id
    @Column(name = "user_id")
    private Integer id;  

    @Column(name = "description", length = 1000, nullable = true)
    private String description;

    @Column(name = "phoneNumber", nullable = true)
    private String phoneNumber;

    @Column(name = "address", length = 255, nullable = true)
    private String address;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
