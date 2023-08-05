package com.personal.businessprofile.api.controller;

import com.personal.businessprofile.api.ErrorApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ErrorController implements ErrorApi {

    @Override
    public ResponseEntity<?> fail() {
        return ResponseEntity.ok(Map.of(
          "error", "Site maintenance in progress"));
    }
}
