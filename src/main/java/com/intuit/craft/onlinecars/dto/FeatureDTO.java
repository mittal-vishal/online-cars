package com.intuit.craft.onlinecars.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeatureDTO implements Serializable {

    private Boolean isPowerSteering;
    private Boolean isCruiseControl;
    private Boolean isKeylessEntry;
    private Boolean isRearACVents;

}
