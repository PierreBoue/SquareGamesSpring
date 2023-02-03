package com.macaplix.squareGames.dto;

import com.macaplix.squareGames.security.Roles;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public record UserDTO(String username, String token, Collection<? extends GrantedAuthority> roles ) {
}
