package com.intuit.craft.onlinecars.mapper;

import com.intuit.craft.onlinecars.dto.CarDTO;
import com.intuit.craft.onlinecars.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarMapper {

    @Mapping(source = "car.id", target = "id")
    @Mapping(source = "car.carEntityKey.brand", target = "brand")
    @Mapping(source = "car.carEntityKey.name", target = "name")
    @Mapping(source = "car.specification.mileage", target = "specification.mileage")
    @Mapping(source = "car.specification.fuelType", target = "specification.fuelType")
    @Mapping(source = "car.specification.numOfCylinder", target = "specification.numOfCylinder")
    @Mapping(source = "car.specification.engineDisplacement", target = "specification.engineDisplacement")
    @Mapping(source = "car.specification.bhp", target = "specification.enginePower.bhp")
    @Mapping(source = "car.specification.rpm", target = "specification.enginePower.rpm")
    @Mapping(source = "car.specification.seatCapacity", target = "specification.seatCapacity")
    @Mapping(source = "car.specification.bootSpace", target = "specification.bootSpace")
    @Mapping(source = "car.specification.transmissionType", target = "specification.transmissionType")
    @Mapping(source = "car.feature.isPowerSteering", target = "feature.isPowerSteering")
    @Mapping(source = "car.feature.isCruiseControl", target = "feature.isCruiseControl")
    @Mapping(source = "car.feature.isKeylessEntry", target = "feature.isKeylessEntry")
    @Mapping(source = "car.feature.isRearACVents", target = "feature.isRearACVents")
    CarDTO toCarDTO(Car car);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "carEntityKey.name")
    @Mapping(source = "brand", target = "carEntityKey.brand")
    @Mapping(source = "specification.mileage", target = "specification.mileage")
    @Mapping(source = "specification.fuelType", target = "specification.fuelType")
    @Mapping(source = "specification.numOfCylinder", target = "specification.numOfCylinder")
    @Mapping(source = "specification.engineDisplacement", target = "specification.engineDisplacement")
    @Mapping(source = "specification.enginePower.bhp", target = "specification.bhp")
    @Mapping(source = "specification.enginePower.rpm", target = "specification.rpm")
    @Mapping(source = "specification.seatCapacity", target = "specification.seatCapacity")
    @Mapping(source = "specification.bootSpace", target = "specification.bootSpace")
    @Mapping(source = "specification.transmissionType", target = "specification.transmissionType")
    @Mapping(source = "feature.isPowerSteering", target = "feature.isPowerSteering")
    @Mapping(source = "feature.isCruiseControl", target = "feature.isCruiseControl")
    @Mapping(source = "feature.isKeylessEntry", target = "feature.isKeylessEntry")
    @Mapping(source = "feature.isRearACVents", target = "feature.isRearACVents")
    Car toCarEntity(CarDTO carDTO);

}
