package org.example.infra.author;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "user-service")
public interface FeignFetchAuthorClient {

    @RequestMapping(method = RequestMethod.GET, value = "/api/users/{userId}", produces = "application/json")
    AuthorResponse fetchById(@PathVariable("userId") String userId);
}
