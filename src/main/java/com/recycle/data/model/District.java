package com.recycle.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter@Setter
public class District extends Location{
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    @ManyToOne
    private City city;

    public District() {
        super();
    }

    public District(Long id, String name, City city) {
        super(id, name);
        this.city = city;
    }
}
