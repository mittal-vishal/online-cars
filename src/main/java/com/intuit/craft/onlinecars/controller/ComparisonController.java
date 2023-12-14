package com.intuit.craft.onlinecars.controller;

import com.google.gson.JsonElement;
import com.intuit.craft.onlinecars.dto.request.ComparisonRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping
@Validated
public interface ComparisonController {

    @PostMapping(value = "/compare")
    @ApiOperation(value = "Compare Car by ID", notes = "This API is to compare the Car")
    ResponseEntity<List<JsonElement>> compare(@Valid @RequestBody ComparisonRequest request,
                                              @RequestParam(value = "isHide", required = false) boolean isHide);

}
