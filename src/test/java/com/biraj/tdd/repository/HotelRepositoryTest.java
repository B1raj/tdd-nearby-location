package com.biraj.tdd.repository;

import com.biraj.tdd.model.Hotel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class HotelRepositoryTest {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void gets_hotel_by_id() {
        Hotel savedHotel = testEntityManager.persistAndFlush(Hotel.builder().id("20").name("Novotel").isActive(true).build());

        Optional<Hotel> byId = hotelRepository.findById("20");
        assertThat(byId).isNotEmpty();
        assertThat(byId.get()).isEqualTo(savedHotel);
    }

    @Test
    public void saves_hotel() {
        Hotel savedHotel = Hotel.builder().id("1").name("Novotel").isActive(true).build();

        Hotel save = hotelRepository.save(savedHotel);
        assertThat(save).isEqualTo(savedHotel);

    }


}
