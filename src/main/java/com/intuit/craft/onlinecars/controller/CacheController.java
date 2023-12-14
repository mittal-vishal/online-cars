package com.intuit.craft.onlinecars.controller;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping
public interface CacheController {

    @PostMapping(value = "/evictCache")
    @ApiOperation(value = "Evict Cache", notes = "This API is to evict the cache")
    public ResponseEntity<Void> evictCache();

}
