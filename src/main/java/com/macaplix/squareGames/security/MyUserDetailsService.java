package com.macaplix.squareGames.security;

import com.macaplix.squareGames.dao.UserDAO;
import com.macaplix.squareGames.entities.UserEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    AuthenticationConfiguration authenticationConfiguration;
    @Override
    public UserDetails loadUserByUsername(String username)
    {
        return userDAO.findByUsername(username);
    }
    @Bean
    public AuthenticationManager authenticationManager() {
        try {
            return authenticationConfiguration.getAuthenticationManager();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @PostConstruct
    private void populateUserDatabase()
    {
       Iterable<UserEntity> userIt = userDAO.findAll();
       if ( ! userIt.iterator().hasNext())
       {
           UserEntity userE = new UserEntity();
           userE.setUsername("piero");
            userE.setPassword("nimp");
            userE.setAccountNonLocked(true);
            userE.setEnabled(true);
            userE.setAccountNotExpired(true);
            userE.setCredentialsNonExpired( true );
            userDAO.save(userE);
       }
    }
}
