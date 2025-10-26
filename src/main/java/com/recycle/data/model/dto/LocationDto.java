package com.recycle.data.model.dto;

import com.recycle.data.model.Location;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class LocationDto {
    private Long id;
    private String name;

    public LocationDto(Location location) {
        this.id = location.getId();
        this.name = location.getName();
    }
}
