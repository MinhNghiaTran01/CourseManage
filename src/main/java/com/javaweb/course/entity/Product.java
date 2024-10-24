package com.javaweb.course.entity;

import javax.persistence.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 128)
    @NotNull @Length(min = 5, max = 128)
    private String name;

    private float price;

    public @NotNull @Length(min = 5, max = 128) String getName() {
        return name;
    }

    public void setName(@NotNull @Length(min = 5, max = 128) String name) {
        this.name = name;
    }

}