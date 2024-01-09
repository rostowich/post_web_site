package org.example.domain.user.fetch;

import org.example.domain.user.model.User;

import java.util.List;
import java.util.Optional;

public interface FetchUserRepository {
    Optional<User> getByEmail(String email);
    List<User> getAll();
    Optional<User> getById(Long userId);
}
