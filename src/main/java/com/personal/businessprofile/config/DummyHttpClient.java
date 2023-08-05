package com.personal.businessprofile.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class DummyHttpClient {

    private final Environment environment;

    @Autowired
    public DummyHttpClient(Environment environment) {
        this.environment = environment;
    }

    public ResponseEntity<?> callExternalApi() {
        boolean isSuccess = environment.getProperty("external.api.response.success", Boolean.class, true);

        if (isSuccess) {
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        }
    }
}
