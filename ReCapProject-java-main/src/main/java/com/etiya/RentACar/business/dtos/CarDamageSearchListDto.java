package com.etiya.RentACar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDamageSearchListDto {

   // private int carDamageId;;
    private int id;
    private String damageDescription;
}
