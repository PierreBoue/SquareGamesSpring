package com.macaplix.squareGames.security;

import com.macaplix.squareGames.dao.UserDAO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter
{
    //final String header = request.getHeader("Authorization");
    //final String token = header.split(" ")[1].trim();
    @Autowired
    UserDAO userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        filterChain.doFilter(request, response);
    }

}
