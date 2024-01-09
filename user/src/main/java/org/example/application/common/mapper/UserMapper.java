package org.example.application.common.mapper;


import org.example.application.model.AddUserRequest;
import org.example.application.model.UserResponse;
import org.example.domain.user.model.LightUser;
import org.example.domain.user.model.User;

public final class UserMapper {

    private UserMapper() {
    }

    public static LightUser mapToLightUser(AddUserRequest addUserRequest){
        return LightUser.builder()
                .email(addUserRequest.email())
                .password(addUserRequest.password())
                .firstname(addUserRequest.firstname())
                .lastname(addUserRequest.lastname())
                .build();
    }

    public static UserResponse mapToUserResponse(User user){
        return UserResponse.builder()
                .id(user.id())
                .email(user.email())
                .firstname(user.firstname())
                .lastname(user.lastname())
                .role(user.role())
                .creationDate(user.creationDate())
                .build();
    }
}
