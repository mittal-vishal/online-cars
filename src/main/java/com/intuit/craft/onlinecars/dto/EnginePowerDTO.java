package com.intuit.craft.onlinecars.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnginePowerDTO implements Serializable {

    @NotNull
    private Double bhp;
    @NotNull
    private Integer rpm;

}
