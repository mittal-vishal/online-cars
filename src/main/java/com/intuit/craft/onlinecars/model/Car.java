package com.intuit.craft.onlinecars.model;

import com.intuit.craft.onlinecars.enums.CarType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "car")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Car implements Serializable {

    private String id;
    @EmbeddedId
    private CarEntityKey carEntityKey;
    @Column(nullable = false, name = "car_type")
    @Enumerated(EnumType.STRING)
    private CarType carType;
    @Column(nullable = false)
    private Long price;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_specs_id")
    private Specification specification;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_feature_id")
    private Feature feature;
}
