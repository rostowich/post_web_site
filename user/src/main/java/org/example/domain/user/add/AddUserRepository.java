package org.example.domain.user.add;

import org.example.domain.user.model.LightUser;
import org.example.domain.user.model.User;

public interface AddUserRepository {

    User add(LightUser lightUser);
}
