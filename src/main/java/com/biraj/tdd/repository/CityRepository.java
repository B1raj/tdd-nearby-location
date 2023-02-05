package com.biraj.tdd.repository;

import com.biraj.tdd.model.City;
import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<City, String> {

}
