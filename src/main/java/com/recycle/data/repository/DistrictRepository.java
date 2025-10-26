package com.recycle.data.repository;

import com.recycle.data.model.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistrictRepository extends JpaRepository<District, Long> {
    List<District> findByCityIdIn(List<Long> cityIds);
}
