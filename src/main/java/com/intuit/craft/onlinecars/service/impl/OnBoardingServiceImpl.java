package com.intuit.craft.onlinecars.service.impl;

import com.intuit.craft.onlinecars.constant.Constant;
import com.intuit.craft.onlinecars.dto.CarDTO;
import com.intuit.craft.onlinecars.exception.ExceptionCodes;
import com.intuit.craft.onlinecars.mapper.CarMapper;
import com.intuit.craft.onlinecars.model.Car;
import com.intuit.craft.onlinecars.model.CarEntityKey;
import com.intuit.craft.onlinecars.repository.CarRepository;
import com.intuit.craft.onlinecars.service.OnBoardingService;
import com.intuit.craft.onlinecars.service.SimilarityScoreService;
import com.intuit.craft.onlinecars.utils.GsonUtils;
import com.intuit.craft.onlinecars.utils.ValidationHelper;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OnBoardingServiceImpl implements OnBoardingService {

    private final CarRepository carRepository;
    private SimilarityScoreService similarityScoreService;
    private CarMapper carMapper;
    private RedisTemplate<String,Object> redisTemplate;
    private KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    public OnBoardingServiceImpl(CarRepository carRepository, SimilarityScoreService similarityScoreService,
                             CarMapper carMapper, RedisTemplate<String,Object> redisTemplate,
                                 KafkaTemplate<String,String> kafkaTemplate){
        this.carRepository = carRepository;
        this.similarityScoreService = similarityScoreService;
        this.carMapper = carMapper;
        this.redisTemplate = redisTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    @Retry(name = Constant.ONLINE_CAR_SERVICE_NAME)
    public void createCar(CarDTO carDTO) {
        carDTO.setId(UUID.randomUUID().toString());
        log.info("Create car with id {}", carDTO.getId());
        //If already exist with same ID, return
        if(Objects.nonNull(redisTemplate.opsForHash().get(Constant.REDIS_HASH_CAR, carDTO.getId())) ||
                carRepository.findById(CarEntityKey.builder()
                        .brand(carDTO.getBrand()).name(carDTO.getName()).build()).isPresent()) return;
        Car entityCar = carMapper.toCarEntity(carDTO);
        carRepository.save(entityCar);
        redisTemplate.opsForHash().put(Constant.REDIS_HASH_CAR, carDTO.getId(), carDTO);
        //publish an event to Kafka
        this.kafkaTemplate.send(Constant.KAFKA_TOPIC_CAR_ONBOARDED, carDTO.getId());
    }

    @Override
    public CarDTO getCar(String id) {
        CarDTO carDTO = (CarDTO) redisTemplate.opsForHash().get(Constant.REDIS_HASH_CAR, id);
        if(Objects.nonNull(carDTO)) return carDTO;
        CarDTO car =  carRepository.getCarByID(id)
                .map(carEntity -> {
                    CarDTO mappedCar = carMapper.toCarDTO(carEntity);
                    redisTemplate.opsForHash().put(Constant.REDIS_HASH_CAR, mappedCar.getId(), mappedCar);
                    return mappedCar;
                })
                .orElse(null);
        ValidationHelper.notNull(car, ExceptionCodes.V102,"Car with code: " + id + " not found");
        return car;
    }

    @Override
    public List<CarDTO> getCars() {
        List<CarDTO> cars = (List<CarDTO>)(Object) redisTemplate.opsForHash().values(Constant.REDIS_HASH_CAR);
        if(cars.size() > 0) return cars;
        return carRepository.findAll()
                .stream().map(carEntity -> carMapper.toCarDTO(carEntity))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCar(String id) {
        redisTemplate.opsForHash().delete(Constant.REDIS_HASH_CAR, id);
        carRepository.deleteCarByID(id);
    }

    @Override
    public void updateCar(CarDTO carDTO) {
        Car car = carMapper.toCarEntity(carDTO);
        redisTemplate.opsForHash().put(Constant.REDIS_HASH_CAR, carDTO.getId(), carDTO);
        carRepository.save(car);
    }
}
