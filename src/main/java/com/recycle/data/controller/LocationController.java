package com.recycle.data.controller;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recycle.data.model.State;
import com.recycle.data.model.dto.LocationDto;
import com.recycle.data.model.request.LocationRequest;
import com.recycle.data.service.LocalizationService;

@RestController
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private LocalizationService service;

    @GetMapping("/states")
    public ResponseEntity<List<State>> getStates() {
        return new ResponseEntity<>(service.getStates(), HttpStatus.OK);
    }

    @GetMapping("/cities/{stateId}")
    public ResponseEntity<List<LocationDto>> getCities(@PathVariable Long stateId) {
        return new ResponseEntity<>(service.getCities(stateId), HttpStatus.OK);
    }

    @GetMapping("/cities/process")
    public ResponseEntity<List<LocationDto>> saveCities(@ModelAttribute LocationRequest request) {
        return this.execute(() -> service.consumeApiCitiesByStates(request));
    }

    @GetMapping("/districts/process")
    public ResponseEntity<List<LocationDto>> executeDistricts(@ModelAttribute LocationRequest request) {
        return this.execute(() -> service.consumeApiDistrictsByCities(request));
    }


    private ResponseEntity<List<LocationDto>> execute(Supplier<List<LocationDto>> supplier) {
        try {
            return new ResponseEntity<>(supplier.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        
    }
}
