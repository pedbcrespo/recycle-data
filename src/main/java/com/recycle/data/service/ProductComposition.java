package com.recycle.data.service;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@Document("product_composition")  
public class ProductComposition {
    @Id
    private String id;
    private Long productId;
    private String description; 
    private List<String> composition;
    private Instant createDate;
}
