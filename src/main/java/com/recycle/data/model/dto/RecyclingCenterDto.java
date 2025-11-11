package com.recycle.data.model.dto;

import java.util.List;

import com.recycle.data.model.ProductType;
import com.recycle.data.model.RecyclingCenter;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class RecyclingCenterDto {
    private String name;
    private String address;
    private List<ProductType> types;

    public RecyclingCenterDto(RecyclingCenter recyclingCenter) {
        this.name = recyclingCenter.getName();
        this.address = recyclingCenter.getAddress();
        this.types = recyclingCenter.getRecycleProductType().stream().toList();
    }
}