package org.example.domain.user.fetch;

import org.example.domain.common.exceptions.NotFoundException;
import org.example.domain.user.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FetchUserService {
    private final FetchUserRepository fetchUserRepository;

    public FetchUserService(FetchUserRepository fetchUserRepository) {
        this.fetchUserRepository = fetchUserRepository;
    }

    public List<User> All(){
        return fetchUserRepository.getAll();
    }

    public User byId(Long userId){
        return fetchUserRepository.getById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
    }
}
