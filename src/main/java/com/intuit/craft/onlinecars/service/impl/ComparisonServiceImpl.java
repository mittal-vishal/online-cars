package com.intuit.craft.onlinecars.service.impl;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.intuit.craft.onlinecars.constant.Constant;
import com.intuit.craft.onlinecars.dto.CarDTO;
import com.intuit.craft.onlinecars.exception.ExceptionCodes;
import com.intuit.craft.onlinecars.mapper.CarMapper;
import com.intuit.craft.onlinecars.repository.CarRepository;
import com.intuit.craft.onlinecars.service.ComparisonService;
import com.intuit.craft.onlinecars.utils.GsonUtils;
import com.intuit.craft.onlinecars.utils.ValidationHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ComparisonServiceImpl implements ComparisonService {

    private RedisTemplate<String,Object> redisTemplate;
    private CarRepository carRepo;
    private CarMapper carMapper;

    @Autowired
    public ComparisonServiceImpl(RedisTemplate<String,Object> redisTemplate,
                                 CarRepository carRepo, CarMapper carMapper){
        this.redisTemplate = redisTemplate;
        this.carRepo = carRepo;
        this.carMapper = carMapper;
    }

    @Override
    public List<JsonElement> compare(List<String> carIDs, boolean isHide) {
        List<CarDTO> cars = new ArrayList<>();
        for(String carID: carIDs){
            CarDTO carDTO = (CarDTO) redisTemplate.opsForHash().get(Constant.REDIS_HASH_CAR, carID);
            if(Objects.isNull(carDTO)){
                carDTO = carRepo.getCarByID(carID)
                        .map(carEntity -> carMapper.toCarDTO(carEntity))
                        .orElse(null);
                ValidationHelper.notNull(carDTO, ExceptionCodes.V102,"Car with code: " +carID+ " not found");
            }
            cars.add(carDTO);
        }
        List<JsonElement> result = isHide ? getFilteredResult(cars) : getNonFilteredResult(cars);
        return result;
    }

    private List<JsonElement> getNonFilteredResult(List<CarDTO> cars) {
        return cars.stream()
                .map(car -> GsonUtils.convertToJsonElement(car))
                .collect(Collectors.toList());
    }

    private List<JsonElement> getFilteredResult(List<CarDTO> cars) {
        List<JsonElement> jsonElements = getNonFilteredResult(cars);
        HashMap<String, HashSet<Boolean>> uniqueFeatures = new HashMap<>();
        for(JsonElement jsonElement: jsonElements){
            JsonObject jsonObject = jsonElement.getAsJsonObject().get(Constant.CAR_FEATURE_KEY).getAsJsonObject();
            for(Map.Entry<String,JsonElement> entry: jsonObject.entrySet()){
                uniqueFeatures.putIfAbsent(entry.getKey(), new HashSet<>());
                uniqueFeatures.get(entry.getKey()).add(Boolean.valueOf(entry.getValue().getAsBoolean()));
            }
        }
        uniqueFeatures.entrySet().stream()
                .filter(entry -> entry.getValue().size() == 1)
                .forEach(entry -> {
                    jsonElements.forEach(
                            element -> element.getAsJsonObject().get(Constant.CAR_FEATURE_KEY)
                                    .getAsJsonObject().remove(entry.getKey()));
                });
        return jsonElements;
    }
}
