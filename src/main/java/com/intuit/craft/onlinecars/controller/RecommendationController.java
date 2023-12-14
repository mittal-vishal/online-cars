package com.intuit.craft.onlinecars.controller;

import com.intuit.craft.onlinecars.dto.CarDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/recommend")
@Validated
public interface RecommendationController {

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Generate recommendation by Car ID", notes =
            "This API is to generate the recommendation by Car ID")
    ResponseEntity<List<CarDTO>> getRecommendation(@Valid @PathVariable String id,
                                                   @RequestParam(required = false) Integer count);

}
