package com.ejder.chatapp.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
}