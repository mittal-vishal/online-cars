package com.intuit.craft.onlinecars.controller.impl;

import com.intuit.craft.onlinecars.constant.Constant;
import com.intuit.craft.onlinecars.controller.RecommendationController;
import com.intuit.craft.onlinecars.dto.CarDTO;
import com.intuit.craft.onlinecars.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class RecommendationControllerImpl implements RecommendationController {

    private RecommendationService recommendationService;

    @Autowired
    public RecommendationControllerImpl(RecommendationService recommendationService){
        this.recommendationService = recommendationService;
    }

    @Override
    public ResponseEntity<List<CarDTO>> getRecommendation(String id, Integer count) {
        count = Objects.isNull(count)? Constant.RECOMMENDATION_COUNT : count;
        List<CarDTO> recommendedCars = recommendationService.getRecommendation(id, count);
        return ResponseEntity.status(HttpStatus.OK).body(recommendedCars);
    }

}
