package com.intuit.craft.onlinecars.service;

import com.intuit.craft.onlinecars.dto.CarDTO;

import java.util.List;

public interface OnBoardingService {

    void createCar(CarDTO carDTO);
    CarDTO getCar(String id);
    List<CarDTO> getCars();
    void deleteCar(String id);
    void updateCar(CarDTO carDTO);
}
