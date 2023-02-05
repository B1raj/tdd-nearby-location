package com.biraj.tdd.service;

import com.biraj.tdd.model.City;
import com.biraj.tdd.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CityService {
    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public Optional<City> search(String cityId) {
        return cityRepository.findById(cityId);
    }
}
