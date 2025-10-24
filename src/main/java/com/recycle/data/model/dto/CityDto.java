package com.recycle.data.model.dto;

import com.recycle.data.model.City;
import com.recycle.data.model.State;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class CityDto extends LocationDto<City, State>{
    private Long id;
    private String nome;

    @Override
    public City generate(State state) {
        return new City(id, nome, state);
    }
}
