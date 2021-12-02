package com.etiya.RentACar.business.requests.carDamage;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CreateCarDamageRequest {
    @JsonIgnore
    private int carDamageId;

    @NotNull
    private int carId;

    @NotNull
    private String damageInformation;
}
