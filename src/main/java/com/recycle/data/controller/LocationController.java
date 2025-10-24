package com.recycle.data.controller;

import com.recycle.data.service.LocalizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/locations")
public class LocationController {

    @Autowired
    private LocalizationService service;

    @GetMapping("/cities")
    public ResponseEntity<String> saveCities() {
        return this.execute(() -> service.consumeApiCitiesByStates());
    }

    @GetMapping("/districts")
    public ResponseEntity<String> executeDistricts() {
        return this.execute(() -> service.consumeApiDistrictsByCities());
    }


    private ResponseEntity<String> execute(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
