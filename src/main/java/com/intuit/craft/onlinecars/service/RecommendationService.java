package com.intuit.craft.onlinecars.service;

import com.intuit.craft.onlinecars.dto.CarDTO;
import com.intuit.craft.onlinecars.exception.NotFoundException;

import java.util.List;

public interface RecommendationService {

    List<CarDTO> getRecommendation(String id, Integer count);

}
