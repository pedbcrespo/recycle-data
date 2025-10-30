package com.recycle.data.model;

import com.recycle.data.enums.Region;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter@Setter
public class State extends Location {
    private String uf;
    @Enumerated(EnumType.STRING)
    private Region region;

    public State() {
        super();
    }
}
