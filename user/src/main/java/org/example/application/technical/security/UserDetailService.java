package org.example.application.technical.security;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.common.exceptions.NotFoundException;
import org.example.domain.user.fetch.FetchUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserDetailService implements UserDetailsService {

    private final FetchUserRepository fetchUserRepository;

    public UserDetailService(FetchUserRepository fetchUserRepository) {
        this.fetchUserRepository = fetchUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var userFound = fetchUserRepository.getByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + email));

        GrantedAuthority authority = new SimpleGrantedAuthority(userFound.role());
        return new CustomUser(userFound.id(), userFound.email(), userFound.password(), List.of(authority));
    }
}