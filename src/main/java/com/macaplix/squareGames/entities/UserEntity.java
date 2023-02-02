package com.macaplix.squareGames.entities;

import jakarta.persistence.*;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> authorities = new ArrayList<>();
    private String password;
    private String username;
    private boolean isAccountNotExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return authorities.stream().map(s -> (GrantedAuthority) () -> s).collect(Collectors.toList());//AuthorityUtils.class( authorities );
    }

    public void addGrantedAuthoity( String gauth)
    {
        authorities.add(gauth);
    }

    public String getPassword()
    {
        return password;
    }
    public void setPassword(String passw )
    {
        this.password = passw;
    }
    public String getUsername()
    {
        return username;
    }
    public void setUsername( String username)
    {
        this.username = username;
    }

    public boolean isAccountNonExpired()
    {
        return isAccountNotExpired;
    }

    public void setAccountNotExpired(boolean accountNotExpired)
    {
        isAccountNotExpired = accountNotExpired;
    }

    public boolean isAccountNonLocked()
    {
        return isAccountNonLocked;
    }
    public void setAccountNonLocked(boolean accountNonLocked)
    {
        isAccountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired()
    {
        return isCredentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired)
    {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled()
    {
        return isEnabled;
    }

    public void setEnabled(boolean enabled)
    {
        isEnabled = enabled;
    }
}
