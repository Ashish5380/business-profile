package com.intuit.businessprofile.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@RequestMapping("/error")
public interface ErrorApi {

    @GetMapping("/api-failure")
    ResponseEntity<?> fail();
}
