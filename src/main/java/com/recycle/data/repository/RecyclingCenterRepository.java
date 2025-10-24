package com.recycle.data.repository;

import com.recycle.data.model.RecyclingCenter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecyclingCenterRepository extends JpaRepository<RecyclingCenter, Long> {
}
