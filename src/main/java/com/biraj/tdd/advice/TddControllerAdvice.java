package com.biraj.tdd.advice;

import com.biraj.tdd.exception.CityNotFoundExcpetion;
import com.biraj.tdd.exception.HotelNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class TddControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(HotelNotFoundException.class)
    @ResponseBody
    void handleHotelNotFoundException(HttpServletRequest req) {

    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CityNotFoundExcpetion.class)
    @ResponseBody
    void handleCityNotFoundException(HttpServletRequest req) {

    }
}
