package com.intuit.craft.onlinecars.service.impl;

import com.intuit.craft.onlinecars.constant.Constant;
import com.intuit.craft.onlinecars.dto.CarDTO;
import com.intuit.craft.onlinecars.model.SimilarityScore;
import com.intuit.craft.onlinecars.repository.SimilarityScoreRepository;
import com.intuit.craft.onlinecars.service.SimilarityScoreService;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SimilarityScoreServiceImpl implements SimilarityScoreService {

    private SimilarityScoreRepository similarityScoreRepository;

    @Value("${online-car.similarity.criteria}")
    private List<String> criterias;

    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    public SimilarityScoreServiceImpl(SimilarityScoreRepository similarityScoreRepository,
                                      RedisTemplate<String,Object> redisTemplate){
        this.similarityScoreRepository = similarityScoreRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    @Retry(name = Constant.ONLINE_CAR_SERVICE_NAME)
    public void updateScore(CarDTO currentCar, List<CarDTO> existingCars) {
        //update existing car similarity score
        List<SimilarityScore> existingSimilarityScore = similarityScoreRepository.findAllExceptCurrentID(currentCar.getId());
        HashMap<String, SimilarityScore> existingSimilarityScoreMap = new HashMap<>();
        existingSimilarityScore.stream().forEach(similarityScore -> existingSimilarityScoreMap.put(similarityScore.getId(), similarityScore));
        HashMap<String, Double> similarityScoreForCurrCar = new HashMap<>();
        existingCars.stream()
                .forEach(existingCar -> {
                    Double score = calculateScore(currentCar, existingCar);
                    similarityScoreForCurrCar.put(existingCar.getId(), score);
                    if(existingSimilarityScoreMap.containsKey(existingCar.getId())){
                        existingSimilarityScoreMap.get(existingCar.getId()).getData().putIfAbsent(currentCar.getId(), score);
                    }
                });
        similarityScoreRepository.saveAll(new ArrayList<>(existingSimilarityScoreMap.values()));
        //update current car similarity score
        SimilarityScore currCarSimilarityScore = SimilarityScore.builder()
                .id(currentCar.getId())
                .data(similarityScoreForCurrCar).build();
        similarityScoreRepository.save(currCarSimilarityScore);
        //update in similarity cache
        existingSimilarityScoreMap.values().stream()
                .forEach(similarityScore -> redisTemplate.opsForHash()
                        .put(Constant.REDIS_HASH_SIMILARITY, similarityScore.getId(), similarityScore));
        redisTemplate.opsForHash()
                .put(Constant.REDIS_HASH_SIMILARITY, currCarSimilarityScore.getId(), currCarSimilarityScore);
    }

    private Double calculateScore(CarDTO srcCar, CarDTO destCar){
        Double result = 0d;
        result += criterias.contains(Constant.SIMILARITY_CRITERIA_BRAND) ?
                compareBrand(srcCar.getBrand(), destCar.getBrand()) : 0d;
        result += criterias.contains(Constant.SIMILARITY_CRITERIA_MILEAGE) ?
                compareMileage(srcCar.getSpecification().getMileage(), destCar.getSpecification().getMileage()): 0d;
        result += criterias.contains(Constant.SIMILARITY_CRITERIA_PRICE) ?
                comparePrice(srcCar.getPrice(), destCar.getPrice()): 0d;
        result /= (criterias.size() * 1d);
        return result;
    }

    private Double compareBrand(String srcBrand, String destBrand){
        return srcBrand.equals(destBrand)? 1d: 0d;
    }

    private Double compareMileage(Double srcMileage, Double destMileage){
        return 1d - (Math.abs(srcMileage-destMileage) / 100);
    }

    private Double comparePrice(Long srcPrice, Long destPrice){
        Long priceDiff = Math.abs(srcPrice - destPrice);
        if(priceDiff == 0L){
            return 1D;
        }
        Long maxCarPrice = srcPrice > destPrice? srcPrice: destPrice;
        Double diffInDecimal = priceDiff / Math.pow(10, getNumberOfDigit(maxCarPrice));
        return 1D - diffInDecimal;
    }

    private Double getNumberOfDigit(long price){
        Double count = 0d;
        while(price > 0){
            price /= 10;
            count++;
        }
        return count;
    }
}
