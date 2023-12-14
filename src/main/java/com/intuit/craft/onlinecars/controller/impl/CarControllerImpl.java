package com.intuit.craft.onlinecars.controller.impl;

import com.intuit.craft.onlinecars.controller.CarController;
import com.intuit.craft.onlinecars.dto.CarDTO;
import com.intuit.craft.onlinecars.service.OnBoardingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CarControllerImpl implements CarController {

    private OnBoardingService onBoardingService;

    @Autowired
    public CarControllerImpl(OnBoardingService onBoardingService){
        this.onBoardingService = onBoardingService;
    }

    @Override
    public ResponseEntity<Void> createCar(CarDTO carDTO) {
        onBoardingService.createCar(carDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CarDTO> getCarByID(String id) {
        CarDTO carDTO = onBoardingService.getCar(id);
        return ResponseEntity.status(HttpStatus.OK).body(carDTO);
    }

    @Override
    public ResponseEntity<List<CarDTO>> getCars(){
        List<CarDTO> cars = onBoardingService.getCars();
        return ResponseEntity.status(HttpStatus.OK).body(cars);
    }

    @Override
    public ResponseEntity<Void> deleteCar(String id){
        onBoardingService.deleteCar(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<CarDTO> updateCar(CarDTO carRequest){
        onBoardingService.updateCar(carRequest);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
