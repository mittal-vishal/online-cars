package com.intuit.craft.onlinecars.controller.impl;

import com.google.gson.JsonElement;
import com.intuit.craft.onlinecars.controller.ComparisonController;
import com.intuit.craft.onlinecars.dto.request.ComparisonRequest;
import com.intuit.craft.onlinecars.service.ComparisonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ComparisonControllerImpl implements ComparisonController {

    private ComparisonService comparisonService;

    @Autowired
    public ComparisonControllerImpl(ComparisonService comparisonService){
        this.comparisonService = comparisonService;
    }

    @Override
    public ResponseEntity<List<JsonElement>> compare(ComparisonRequest request, boolean isHide) {
        List<JsonElement> comparedCars = comparisonService.compare(request.getIds(), isHide);
        return ResponseEntity.status(HttpStatus.OK).body(comparedCars);
    }

}
