package com.recycle.data.repository;

import com.recycle.data.enums.Region;
import com.recycle.data.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findByStateRegion(Region region);
}
