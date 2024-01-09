package org.example.domain.user.model;

import lombok.Builder;

@Builder
public record LightUser(
        String email,
        String password,
        String firstname,
        String lastname
) {
    public LightUser withPasswordEncoded(String passwordEncoded){
        return new LightUser(email, passwordEncoded, firstname, lastname);
    }
}
