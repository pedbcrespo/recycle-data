package com.recycle.data.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recycle.data.model.dto.RecyclingCenterDto;
import com.recycle.data.model.request.RecyclingCenterRequest;
import com.recycle.data.service.RecyclingCenterService;



@RestController
@RequestMapping("/recycling-center")
public class RecyclingCenterController {
    @Autowired
    private RecyclingCenterService service;


    @GetMapping
    public ResponseEntity<List<RecyclingCenterDto>> get() {
        List<RecyclingCenterDto> response = service.get().stream().toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    

    @PostMapping
    public ResponseEntity<RecyclingCenterDto> save(@RequestBody RecyclingCenterRequest request) {
        RecyclingCenterDto response = service.save(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
