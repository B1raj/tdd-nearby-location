package com.biraj.tdd.service;

import com.biraj.tdd.exception.HotelNotFoundException;
import com.biraj.tdd.model.City;
import com.biraj.tdd.model.Hotel;
import com.biraj.tdd.repository.CityRepository;
import com.biraj.tdd.repository.HotelRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    private HotelService hotelService;

    private CityService cityService;
    private CityRepository cityRepository;

    @Before
    public void setup() {
        hotelService = new HotelService(hotelRepository);
        cityService = new CityService(cityRepository);
    }

    @Test
    public void it_should_update() {
        Hotel someHotel = Hotel.builder().id("1").isActive(true).build();
        Hotel someUpdatedHotel = Hotel.builder().id("1").isActive(false).build();

        given(hotelRepository.findById(anyString())).willReturn(Optional.of(someHotel));
        given(hotelRepository.save(someHotel)).willReturn(someUpdatedHotel);

        Hotel response = hotelService.delete("1");
        assert response != null;
        assertThat(response.isActive()).isEqualTo(false);
    }

    @Test
    public void it_should_not_update() {
        given(hotelRepository.findById(anyString())).willReturn(Optional.empty());
        Exception exception = assertThrows(HotelNotFoundException.class, () -> {
            hotelService.delete("1");
        });
        assertThat(exception).isInstanceOf(HotelNotFoundException.class);
    }


}
