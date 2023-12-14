package com.intuit.craft.onlinecars.model;

import com.intuit.craft.onlinecars.enums.FuelType;
import com.intuit.craft.onlinecars.enums.TransmissionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "specification")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Specification implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private Double mileage;
    @Column(nullable = false, name = "fuel_type")
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;
    @Column(name = "number_of_cylinder")
    private Integer numOfCylinder;
    @Column(name = "engine_displacement")
    private Integer engineDisplacement;
    @Column(name = "bhp")
    private Double bhp;
    @Column(name = "rpm")
    private Integer rpm;
    @Column(name = "seat_capacity")
    private Integer seatCapacity;
    @Column(name = "boot_space")
    private Integer bootSpace;
    @Column(nullable = false, name = "transmission_type")
    @Enumerated(EnumType.STRING)
    private TransmissionType transmissionType;

}
