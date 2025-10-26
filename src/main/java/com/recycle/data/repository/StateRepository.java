package com.recycle.data.repository;

import com.recycle.data.enums.Region;
import com.recycle.data.model.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StateRepository extends JpaRepository<State, Long> {
    List<State> findByRegion(Region region);
    List<State> findAllByUfIn(List<String> ufs);
}
