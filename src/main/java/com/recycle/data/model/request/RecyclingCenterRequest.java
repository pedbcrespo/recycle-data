package com.recycle.data.model.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class RecyclingCenterRequest {
    private String name;
    private String address;
    private Long cityId;
    private List<Long> typeIds;

    public boolean isAvailableToSave() {
        return name != null && (address != null || cityId != null);
    }
}
