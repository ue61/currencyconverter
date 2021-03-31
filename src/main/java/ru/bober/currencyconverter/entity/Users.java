package ru.bober.currencyconverter.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@Table (name = "users")
@Data
public class Users {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "userid")
        private long userId;
    @Column(name = "username", nullable = false)
        private String username;
    @Column(name = "password", nullable = false)
        private String password;
    @Column(name = "role", nullable = false)
        private String role;
    @Column(name = "enabled", nullable = false)
        private boolean enabled;
    }
