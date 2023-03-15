package com.macaplix.squaregames.httpinterface;

import com.macaplix.squaregames.dto.UserDTO;
import com.macaplix.squaregames.entities.UserEntity;
import com.macaplix.squaregames.security.AuthenticationRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping(path = "api/public")
public class AuthenticationApi {
    private final AuthenticationManager authenticationManager;

    public AuthenticationApi(AuthenticationManager
                                     authenticationManager) {

        this.authenticationManager = authenticationManager;
    }

    //    @Autowired
    //    JwtUtils jwtUtils;

    //    @Autowired
    //    RefreshTokenService refreshTokenService;

    @PostMapping("refresh")
    public ResponseEntity<UserDTO> refresh() {
        return null;
    }

    @PostMapping("login")
    public ResponseEntity<UserDTO> login(@RequestBody @Valid
                                         AuthenticationRequest request) {

        try {
            final Authentication authenticate = authenticationManager

                    .authenticate(

                            new UsernamePasswordAuthenticationToken(

                                    request.getUsername(),

                                    request.getPassword()

                            )
                    );

            final UserEntity user = (UserEntity) authenticate.getPrincipal();
            final Date expiration = new Date(System.currentTimeMillis() + 3600 *

                    1000L);
            final String token = Jwts.builder().setSubject(authenticate

                            .getName()).claim("authorities", authenticate
                            .getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()).setIssuedAt(new Date())
                    .setExpiration(expiration)

                    .signWith(SignatureAlgorithm.HS512,

                            "fakeAgent".getBytes()).compact();

// Ajoute le token à la réponse dans l’entête http
// TODO : conversion du user en UserDTO
            return ResponseEntity.ok()

                    .header(

                            HttpHeaders.AUTHORIZATION,
                            "Bearer " + token

                    )
                    .body(new UserDTO(user.getUsername(), token, null, expiration, "https://media.licdn.com/dms/image/D4E03AQGVYZ5euIGocA/profile-displayphoto-shrink_800_800/0/1671441491557?e=1681344000&v=beta&t=pGZdGiwkNVYyWnhNTpEwyBJpOvEEH5Ufn6RmFA7lVW8",
                            user.getAuthorities()));

        } catch (BadCredentialsException ex) {
            return

                    ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        }
    }
}

