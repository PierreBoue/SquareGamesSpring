package com.macaplix.squaregames.security;

import com.macaplix.squaregames.dao.UserDao;
import com.macaplix.squaregames.entities.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    UserDao userRepository;
    private static final String SECRET = "fakeAgent";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader("Authorization");
        if (header != null) {
            final String token = header.split(" ")[1].trim();
            // On “parse” le token en utilisant la même clé de signature qui sera
            //utilisée pour générer le token à l’authentification (“secret”)
            final Claims claims =
                    Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(token)
                            .getBody();
// On récupère le nom de l’utilisateur indiqué dans l’objet
            final String username = claims.getSubject();
// On récupère les informations de l’utilisateur grâce au repository
            final UserEntity userDetails = userRepository.findByUsername(username
            );
            final UsernamePasswordAuthenticationToken

                    authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null,
                    userDetails == null ?

                            List.of() : userDetails.getAuthorities()

            );
// Ajoute les informations de l’utilisateur
            authentication.setDetails(

                    new WebAuthenticationDetailsSource().buildDetails(request)
            );
// Met à jour le contexte d’authentification
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    public String doGenerateRefreshToken(Map<String, Object> claims, String subject) {
        final int refreshExpirationDateInMs = 9000000;
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationDateInMs))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();

    }

}
