package com.biraj.tdd.service;

import com.biraj.tdd.exception.HotelNotFoundException;
import com.biraj.tdd.model.City;
import com.biraj.tdd.model.Hotel;
import com.biraj.tdd.repository.HotelRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    private HotelService hotelService;

    @Before
    public void setup() {
        hotelService = new HotelService(hotelRepository);
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
        Exception exception = assertThrows(HotelNotFoundException.class, () -> hotelService.delete("1"));
        assertThat(exception).isInstanceOf(HotelNotFoundException.class);
    }

    @Test
    public void it_should_return_city_names(){
        Hotel hotel = Hotel.builder().name("Novotel").latitude(1.4382).longitude(103.7890).location("payalebar").build();
        Iterable<Hotel> iterable = Collections.singletonList(hotel);
        given(hotelRepository.findAll()).willReturn(iterable);
        List<String> result =  hotelService.search(City.builder().latitude(1.3516).longitude(103.8995).build());
        assertThat(result).isNotNull();
        assertEquals(result.size(),1);
    }

    @Test
    public void it_should_return_distance(){
        Double dist = HotelService.distance(1.3516,103.8995,1.3368,103.6942);
        assertEquals(dist,22.88129404626757);
    }
}
