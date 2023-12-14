package com.intuit.craft.onlinecars.dto;

import com.intuit.craft.onlinecars.enums.FuelType;
import com.intuit.craft.onlinecars.enums.TransmissionType;
import com.intuit.craft.onlinecars.validation.annotation.ValidEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpecificationDTO implements Serializable {

    @NotNull
    private Double mileage;
    @ValidEnum(enumClass = FuelType.class)
    private FuelType fuelType;
    @NotNull
    private Integer numOfCylinder;
    @NotNull
    private Integer engineDisplacement;
    @Valid
    private EnginePowerDTO enginePower;
    @NotNull
    private Integer seatCapacity;
    @NotNull
    private Integer bootSpace;
    @ValidEnum(enumClass = TransmissionType.class)
    private TransmissionType transmissionType;
}
