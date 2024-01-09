package org.example.domain.user.add;

import org.example.domain.common.exceptions.AlreadyExistException;
import org.example.domain.user.fetch.FetchUserRepository;
import org.example.domain.user.model.LightUser;
import org.example.domain.user.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AddUserService {
    private final AddUserRepository addUserRepository;
    private final FetchUserRepository fetchUserRepository;

    private final PasswordEncoder passwordEncoder;

    public AddUserService(AddUserRepository addUserRepository, FetchUserRepository fetchUserRepository, PasswordEncoder passwordEncoder) {
        this.addUserRepository = addUserRepository;
        this.fetchUserRepository = fetchUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User add(LightUser lightUser){
        var userFound = fetchUserRepository.getByEmail(lightUser.email());
        if(userFound.isPresent()){
            throw new AlreadyExistException("A user with this email already exists");
        }

        var userWithPasswordEncoded = lightUser
                .withPasswordEncoded(passwordEncoder.encode(lightUser.password()));
        return addUserRepository.add(userWithPasswordEncoded);
    }
}
