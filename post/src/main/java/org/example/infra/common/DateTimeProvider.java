package org.example.infra.common;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class DateTimeProvider {

    public Timestamp now(){
        return Timestamp.from(Instant.now());
    }
}
