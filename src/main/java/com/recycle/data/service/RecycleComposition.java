package com.recycle.data.service;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document("recycle_composition")
@Getter@Setter
public class RecycleComposition {
    @Id
    private String id;
    private Long recyclingCenterId;
    private List<String> recycleComposition;
    private Instant createDate;
}
