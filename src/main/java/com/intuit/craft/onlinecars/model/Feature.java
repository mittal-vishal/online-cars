package com.intuit.craft.onlinecars.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "feature")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Feature implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "power_steering", columnDefinition = "boolean")
    private Boolean isPowerSteering;
    @Column(name = "cruise_control", columnDefinition = "boolean")
    private Boolean isCruiseControl;
    @Column(name = "keyless_entry", columnDefinition = "boolean")
    private Boolean isKeylessEntry;
    @Column(name = "rear_ac_vents", columnDefinition = "boolean")
    private Boolean isRearACVents;

}
