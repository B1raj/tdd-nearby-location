package com.biraj.tdd.service;

import com.biraj.tdd.exception.HotelNotFoundException;
import com.biraj.tdd.model.City;
import com.biraj.tdd.model.Hotel;
import com.biraj.tdd.model.HotelDistance;
import com.biraj.tdd.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public Hotel delete(String id) {
        Optional<Hotel> hotel = hotelRepository.findById(id);
        if (hotel.isPresent()) {
            Hotel hotel1 = hotel.get();
            hotel1.setActive(false);
            return hotelRepository.save(hotel1);
        } else {
            throw new HotelNotFoundException();
        }
    }

    public List<String> search(City city) {
        Iterable<Hotel> iterable = hotelRepository.findAll();
        Stream<Hotel> stream = StreamSupport.stream(iterable.spliterator(), false);
        return stream.map(e -> new HotelDistance(distance(e.getLatitude(), e.getLongitude(), city.getLatitude(), city.getLongitude()), e.getName(), e.getLocation()))
                .sorted(Comparator.comparingDouble(HotelDistance::getDistance))
                .limit(10)
                .map(HotelDistance::getLocation)
                .collect(Collectors.toList());
    }


    static double distance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }


}
