package com.biraj.tdd.controller;

import com.biraj.tdd.exception.CityNotFoundExcpetion;
import com.biraj.tdd.model.City;
import com.biraj.tdd.service.CityService;
import com.biraj.tdd.service.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class TddController {

    private final HotelService hotelService;
    private final CityService cityService;

    public TddController(HotelService hotelService, CityService cityService) {
        this.hotelService = hotelService;
        this.cityService = cityService;
    }

    @DeleteMapping(path = "/hotels/{id}")
    ResponseEntity<?> deleteHotel(@PathVariable String id) {
        hotelService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(path = "/hotels/search/{cityId}")
    ResponseEntity<?> searchHotels(@PathVariable String cityId) {

        Optional<City> cityOp = cityService.search(cityId);
        if (cityOp.isPresent()) {
            City city = cityOp.get();
            List<String> response = hotelService.search(city);
            return ResponseEntity.ok().body(response);
        } else {
            throw new CityNotFoundExcpetion();
        }
    }
}
