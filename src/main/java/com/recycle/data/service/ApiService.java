package com.recycle.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {

    @Autowired
    private RestTemplate restTemplate;

    public <T> T consume(String url, Class<T> resultObjectClass) {
        return restTemplate.getForObject(url, resultObjectClass);
    }

    public <T> T consume(String url, ParameterizedTypeReference<T> responseType) {
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
        return response.getBody();
    }
}