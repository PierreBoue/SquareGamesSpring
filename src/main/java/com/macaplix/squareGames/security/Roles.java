package com.macaplix.squareGames.security;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public final class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
}
