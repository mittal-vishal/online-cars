package com.intuit.craft.onlinecars.service;

import com.intuit.craft.onlinecars.dto.CarDTO;

import java.util.List;

public interface SimilarityScoreService {

    void updateScore(CarDTO currentCar, List<CarDTO> existingCars);

}
