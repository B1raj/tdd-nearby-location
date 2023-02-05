package com.biraj.tdd.controller;


import com.biraj.tdd.exception.HotelNotFoundException;
import com.biraj.tdd.model.City;
import com.biraj.tdd.model.Hotel;
import com.biraj.tdd.service.CityService;
import com.biraj.tdd.service.HotelService;
import com.sun.tools.javac.util.List;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@NoArgsConstructor
public class TddControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HotelService hotelService;

    @MockBean
    private CityService cityService;

    @Test
    void it_should_delete_hotels() throws Exception {

        Hotel inactivatedHotel = Hotel.builder().name("ABC").id("ABC").isActive(false).build();
        when(hotelService.delete(anyString())).thenReturn(inactivatedHotel);
        mockMvc.perform(MockMvcRequestBuilders.delete("/hotels/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void it_should_not_find_hotels() throws Exception {
        doThrow(new HotelNotFoundException()).when(hotelService).delete("NA");
        mockMvc.perform(MockMvcRequestBuilders.delete("/hotels/NA")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof HotelNotFoundException));
    }

    @Test
    void it_should_return_hotel_locations() throws Exception {

        City c = City.builder().id("1").latitude(1.3516).longitude( 103.8995).name("payaebar").build();

        when(cityService.search(anyString())).thenReturn(Optional.of(c));
        when(hotelService.search(c)).thenReturn(List.of("Aljunid","Tuas","Jurong"));

        mockMvc.perform(MockMvcRequestBuilders.get("/hotels/search/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }


}
