package org.example.infra.repository.user;

import org.example.domain.user.add.AddUserRepository;
import org.example.domain.user.fetch.FetchUserRepository;
import org.example.domain.user.model.LightUser;
import org.example.domain.user.model.User;
import org.example.infra.common.DateTimeProvider;
import org.example.infra.entities.UserEntity;
import org.example.infra.repository.UserEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DatabaseUserRepository implements AddUserRepository, FetchUserRepository {

    private final UserEntityRepository userEntityRepository;
    private final DateTimeProvider dateTimeProvider;

    public DatabaseUserRepository(UserEntityRepository userEntityRepository, DateTimeProvider dateTimeProvider) {
        this.userEntityRepository = userEntityRepository;
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public User add(LightUser lightUser) {
        var userEntity = new UserEntity();
        userEntity.setEmail(lightUser.email());
        userEntity.setFirstname(lightUser.firstname());
        userEntity.setLastname(lightUser.lastname());
        userEntity.setCreationDate(dateTimeProvider.now());
        userEntity.setPassword(lightUser.password());
        userEntity.setRole(Role.USER.name());
        var UserSaved = userEntityRepository.save(userEntity);
        return toUser(UserSaved);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userEntityRepository.findByEmail(email)
                .map(this::toUser);
    }

    @Override
    public List<User> getAll() {
        return userEntityRepository.findAll()
                .stream()
                .map(this::toUser)
                .toList();
    }

    @Override
    public Optional<User> getById(Long userId) {
        return userEntityRepository.findById(userId)
                .map(this::toUser);
    }

    private User toUser(UserEntity userEntity){
        return User.builder()
                .firstname(userEntity.getFirstname())
                .lastname(userEntity.getLastname())
                .creationDate(userEntity.getCreationDate())
                .email(userEntity.getEmail())
                .id(userEntity.getId())
                .role(userEntity.getRole())
                .password(userEntity.getPassword())
                .build();
    }
}
