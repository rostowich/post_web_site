package org.example.infra.common;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.example.domain.common.exceptions.NotFoundException;
import org.springframework.security.access.AccessDeniedException;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {

        return switch (response.status()) {
            case 401, 403 -> new AccessDeniedException("Access denied");
            case 404 -> new NotFoundException("Entity not found");
            default -> new Exception("Unknown error");
        };
    }
}