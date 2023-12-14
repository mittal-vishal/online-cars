package com.intuit.craft.onlinecars.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarEntityKey implements Serializable {

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String brand;

}
