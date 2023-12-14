package com.intuit.craft.onlinecars.dto;

import com.intuit.craft.onlinecars.constant.Constant;
import com.intuit.craft.onlinecars.enums.CarType;
import com.intuit.craft.onlinecars.validation.annotation.ValidEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash(Constant.REDIS_HASH_CAR)
public class CarDTO implements Serializable {

    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String brand;
    @ValidEnum(enumClass = CarType.class)
    private CarType carType;
    @NotNull
    private Long price;
    @Valid
    private SpecificationDTO specification;
    @Valid
    private FeatureDTO feature;
}
