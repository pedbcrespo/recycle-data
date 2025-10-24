package com.recycle.data.controller;

import com.recycle.data.model.Product;
import com.recycle.data.model.ProductType;
import com.recycle.data.model.dto.ProductDto;
import com.recycle.data.model.request.ProductRequest;
import com.recycle.data.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Collection<Product>> get(@ModelAttribute ProductRequest request) {
        Collection<Product> response = productService.get(request);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody ProductDto request) {
        return saveProcess(() -> {
            try {
                productService.register(request);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @PostMapping("/type")
    public ResponseEntity<String> saveType(@RequestBody ProductType productType) {
        return saveProcess(() -> {
            try {
                productService.register(productType);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private ResponseEntity<String> saveProcess(Runnable action) {
        HttpStatus status = HttpStatus.OK;
        String response = "Success";
        try {
            action.run();
        } catch (Exception e) {
            status = HttpStatus.CONFLICT;
            response = "Error! Product not saved!";
        }
        return new ResponseEntity<>(response, status);
    }
}
