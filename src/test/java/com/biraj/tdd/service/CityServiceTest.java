package com.biraj.tdd.service;


import com.biraj.tdd.model.City;
import com.biraj.tdd.repository.CityRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceTest {


    private CityService cityService;

    @Mock
    private CityRepository cityRepository;

    @Before
    public void setup() {
        cityService = new CityService(cityRepository);
    }

    @Test
    public void it_should_search_nearby_cities(){
        City payaLebar = City.builder().id("1").name("Payalebar").longitude(103.8995).latitude(1.3516).build();
        given(cityRepository.findById(anyString())).willReturn(Optional.of(payaLebar));

        Optional<City> city = cityService.search("1");
        assertThat(city).isPresent();
        City c = city.get();
        assertThat(c).isNotNull();
        assertThat(c.getId()).isEqualTo("1");

    }

}
