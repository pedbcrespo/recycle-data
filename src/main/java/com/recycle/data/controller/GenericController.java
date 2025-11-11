package com.recycle.data.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class GenericController {
    @Value("${server.servlet.context-path}")
    private String baseUrl;
    
    @Operation(hidden = true)
    @GetMapping
    public ResponseEntity<Void> redirecionarApi() {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(java.net.URI.create(baseUrl + "/swagger-ui/index.html"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
}   
