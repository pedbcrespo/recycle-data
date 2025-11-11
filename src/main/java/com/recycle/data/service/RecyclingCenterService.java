package com.recycle.data.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recycle.data.model.City;
import com.recycle.data.model.ProductType;
import com.recycle.data.model.RecyclingCenter;
import com.recycle.data.model.dto.RecyclingCenterDto;
import com.recycle.data.model.request.RecyclingCenterRequest;
import com.recycle.data.repository.ProductTypeRepository;
import com.recycle.data.repository.RecyclingCenterRepository;

@Service
public class RecyclingCenterService {
    @Autowired
    private RecyclingCenterRepository recyclingCenterRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private LocalizationService localizationService;

    public Collection<RecyclingCenterDto> get() {
        return recyclingCenterRepository.findAll().stream().map(recyclingCenter -> new RecyclingCenterDto(recyclingCenter)).collect(Collectors.toList());
    }

    public RecyclingCenterDto save(RecyclingCenterRequest request) {
        if(!request.isAvailableToSave()) return null;
        List<ProductType> types = productTypeRepository.findAllById(request.getTypeIds());
        City city = localizationService.getCity(request.getCityId());
        RecyclingCenter recyclingCenter = new RecyclingCenter(request.getName(), request.getAddress(), city, types);
        recyclingCenterRepository.save(recyclingCenter);
        return new RecyclingCenterDto(recyclingCenter);
    }
}
