package org.example.application;

import lombok.extern.slf4j.Slf4j;
import org.example.application.technical.security.AuthenticationRequest;
import org.example.application.technical.security.AuthenticationResponse;
import org.example.application.technical.security.TokenGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@Validated
public class AuthenticationController {
    private final TokenGenerator tokenGenerator;
    private final DaoAuthenticationProvider authenticationProvider;

    public AuthenticationController(TokenGenerator tokenGenerator, DaoAuthenticationProvider authenticationProvider) {
        this.tokenGenerator = tokenGenerator;
        this.authenticationProvider = authenticationProvider;
    }


    @PostMapping()
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        Authentication authentication =
                authenticationProvider
                        .authenticate(new UsernamePasswordAuthenticationToken(
                                authenticationRequest.email(),
                                authenticationRequest.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info("Token requested for user :{}", authentication.getAuthorities());
        String token = tokenGenerator.buildToken(authentication);

        var response = new AuthenticationResponse( token);

        return ResponseEntity.ok(response);
    }
}
