package com.macaplix.squareGames.dto;

import com.macaplix.squareGames.security.Roles;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;

public record UserDTO(String username, String token, String refreshToken, Date expiration, String imgpath, Collection<? extends GrantedAuthority> roles ) {
}
