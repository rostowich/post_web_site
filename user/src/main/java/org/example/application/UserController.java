package org.example.application;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.application.common.mapper.UserMapper;
import org.example.application.model.AddUserRequest;
import org.example.application.model.UserResponse;
import org.example.domain.user.add.AddUserService;
import org.example.domain.user.fetch.FetchUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@Validated
public class UserController {
    private final AddUserService addUserService;
    private final FetchUserService fetchUserService;

    public UserController(AddUserService addUserService, FetchUserService fetchUserService) {
        this.addUserService = addUserService;
        this.fetchUserService = fetchUserService;
    }

    @PostMapping()
    public ResponseEntity<UserResponse> addPost(@RequestBody @Valid AddUserRequest addUserRequest) {
        log.info("Adding a new user with those information : {}", addUserRequest);

        var userToCreate = UserMapper.mapToLightUser(addUserRequest);
        var userCreated = addUserService.add(userToCreate);
        return ResponseEntity.ok(UserMapper.mapToUserResponse(userCreated));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        log.info("Looking for all the users in the database");
        var allUsers = fetchUserService.All();
        return ResponseEntity.ok(allUsers.stream()
                .map(UserMapper::mapToUserResponse)
                .toList());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getById(
            @PathVariable(name = "userId") Long userId
    ) {
        log.info("Looking for the user with id:{}", userId);
        var userFound = fetchUserService.byId(userId);
        return ResponseEntity.ok(UserMapper.mapToUserResponse(userFound));
    }

}
