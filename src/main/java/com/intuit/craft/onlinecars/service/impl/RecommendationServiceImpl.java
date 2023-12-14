package com.intuit.craft.onlinecars.service.impl;

import com.intuit.craft.onlinecars.constant.Constant;
import com.intuit.craft.onlinecars.dto.CarDTO;
import com.intuit.craft.onlinecars.mapper.CarMapper;
import com.intuit.craft.onlinecars.model.SimilarityScore;
import com.intuit.craft.onlinecars.repository.CarRepository;
import com.intuit.craft.onlinecars.repository.SimilarityScoreRepository;
import com.intuit.craft.onlinecars.service.RecommendationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RecommendationServiceImpl implements RecommendationService {

    private RedisTemplate<String,Object> redisTemplate;
    private CarRepository carRepo;
    private SimilarityScoreRepository similarityRepo;
    private CarMapper carMapper;

    @Autowired
    public RecommendationServiceImpl(RedisTemplate<String,Object> redisTemplate,
                                     CarRepository carRepo, SimilarityScoreRepository similarityRepo,
                                     CarMapper carMapper){
        this.redisTemplate = redisTemplate;
        this.carRepo = carRepo;
        this.similarityRepo = similarityRepo;
        this.carMapper = carMapper;
    }

    @Override
    public List<CarDTO> getRecommendation(String id, Integer count) {
        log.info("getRecommendation for id {}", id);
        SimilarityScore currSimilarityScore = (SimilarityScore) redisTemplate.opsForHash()
                .get(Constant.REDIS_HASH_SIMILARITY, id);
        if(Objects.isNull(currSimilarityScore)) currSimilarityScore = similarityRepo.getById(id);
        List<Map.Entry<String,Double>> entries = new ArrayList<>(currSimilarityScore.getData().entrySet())
                .stream().limit(count).sorted((a, b) -> b.getValue()
                        .compareTo(a.getValue())).collect(Collectors.toList());
        List<CarDTO> recommendedCars = entries.stream()
                .map(similarityEntry -> {
                    CarDTO carDTO = (CarDTO) redisTemplate.opsForHash()
                            .get(Constant.REDIS_HASH_CAR,similarityEntry.getKey());
                    if(Objects.isNull(carDTO)){
                        carDTO = carMapper.toCarDTO(carRepo.getCarByID(similarityEntry.getKey()).get());
                    }
                    return carDTO;
                })
                .collect(Collectors.toList());
        return recommendedCars;
    }
}
