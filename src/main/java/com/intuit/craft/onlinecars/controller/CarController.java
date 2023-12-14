package com.intuit.craft.onlinecars.controller;

import com.intuit.craft.onlinecars.dto.CarDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping
@Validated
@SwaggerDefinition
public interface CarController {

    @PostMapping(value = "/car")
    @ApiOperation(value = "Creates a Car", notes = "This API is to create a car")
    ResponseEntity<Void> createCar(@Valid @RequestBody CarDTO carDTO);

    @GetMapping(value = "/car/{id}")
    @ApiOperation(value = "Retrieve a Car using ID", notes = "This API is to retrieve a Car by ID")
    ResponseEntity<CarDTO> getCarByID(@Valid @PathVariable String id);

    @GetMapping(value = "/cars")
    @ApiOperation(value = "Retrieve all Cars", notes = "This API is to retrieve all the Cars")
    ResponseEntity<List<CarDTO>> getCars();

    @DeleteMapping(value = "/car")
    @ApiOperation(value = "Delete a Car using ID", notes = "This API is to delete a Car by ID")
    ResponseEntity<Void> deleteCar(@Valid @RequestBody String id);

    @PutMapping(value = "/car")
    @ApiOperation(value = "Update a Car", notes = "This API is to update a Car")
    ResponseEntity<CarDTO> updateCar(@Valid @RequestBody CarDTO carRequest);

}
