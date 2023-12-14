package com.intuit.craft.onlinecars.consumer;

import com.intuit.craft.onlinecars.constant.Constant;
import com.intuit.craft.onlinecars.dto.CarDTO;
import com.intuit.craft.onlinecars.mapper.CarMapper;
import com.intuit.craft.onlinecars.repository.CarRepository;
import com.intuit.craft.onlinecars.service.SimilarityScoreService;
import com.intuit.craft.onlinecars.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SimilarityScoreConsumer {

    private SimilarityScoreService similarityScoreService;
    private CarRepository carRepository;
    private CarMapper carMapper;

    @Autowired
    public SimilarityScoreConsumer(SimilarityScoreService similarityScoreService, CarMapper carMapper,
                                   CarRepository carRepository){
        this.similarityScoreService = similarityScoreService;
        this.carMapper = carMapper;
        this.carRepository = carRepository;
    }

    @KafkaListener(topics = Constant.KAFKA_TOPIC_CAR_ONBOARDED, containerGroup = Constant.KAFKA_CONSUMER_GROUP)
    public void consumeSimilarityScoreEvent(String eventCarID){
        List<CarDTO> existingCars = carRepository.findAllExceptCurrentID(eventCarID)
                .stream().map(carEntity -> carMapper.toCarDTO(carEntity))
                .collect(Collectors.toList());
        CarDTO srcCar = carMapper.toCarDTO(carRepository.getCarByID(eventCarID)
                .orElse(null));
        if(Objects.isNull(srcCar)){
            return;
        }
        existingCars = CollectionUtils.isEmpty(existingCars)? new ArrayList<>() : existingCars;
        similarityScoreService.updateScore(srcCar, existingCars);
    }

}
