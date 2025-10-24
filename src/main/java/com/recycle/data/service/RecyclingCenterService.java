package com.recycle.data.service;

import com.recycle.data.model.RecyclingCenter;
import com.recycle.data.repository.RecyclingCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RecyclingCenterService {
    @Autowired
    private RecyclingCenterRepository recyclingCenterRepository;

    public Collection<RecyclingCenter> get() {
        return recyclingCenterRepository.findAll();
    }

    public void register(RecyclingCenter recyclingCenter) {
        if(!recyclingCenter.isAvailableToSave()) return;
        recyclingCenterRepository.save(recyclingCenter);
    }
}
