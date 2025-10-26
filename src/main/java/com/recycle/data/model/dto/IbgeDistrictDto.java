package com.recycle.data.model.dto;

import com.recycle.data.model.City;
import com.recycle.data.model.District;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class IbgeDistrictDto extends IbgeLocationDto<District, City> {
    private Long id;
    private String nome;

    @Override
    public District generate(City city) {
        return new District(id, nome, city);
    }
}
