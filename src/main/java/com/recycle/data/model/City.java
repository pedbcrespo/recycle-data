package com.recycle.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter@Setter
public class City extends Location{
    @JoinColumn(name = "state_id", referencedColumnName = "id")
    @ManyToOne
    private State state;

    public City() {
        super();
    }

    public City(Long id, String name, State state) {
        super(id, name);
        this.state = state;
    }
}
