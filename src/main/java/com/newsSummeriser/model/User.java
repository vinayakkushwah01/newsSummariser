package com.newsSummeriser.model;


import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Entity
@Data
@Table(uniqueConstraints =@UniqueConstraint(columnNames={"email"}))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotNull
    String name ;

    @NotNull
    @Email(message = "Invalid email format")
    String email;
    @NotNull
    String password;

    
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role  role ;

    @CreationTimestamp
    private  LocalDateTime createdAt;

}

