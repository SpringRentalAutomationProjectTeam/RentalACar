package com.etiya.RentACar.business.requests.carDamage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DeleteCarDamageRequest {

    @NotNull
    private int carDamageId;

}
