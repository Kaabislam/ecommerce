package com.kaab.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name is required")
    @Size(min = 3, max = 100, message = "Name should be between 3 and 100 characters")
    @Column(unique = true)
    private String name;

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email is required")
    @Column(unique = true)
    private String email;

    @NotEmpty(message = "Password is required")
    @Size(min = 6, message = "Password should be at least 6 characters long")
    private String password;

    @NotEmpty(message = "Roles are required")
    private String roles; // You may want to further refine this, for example using an Enum for roles
}
