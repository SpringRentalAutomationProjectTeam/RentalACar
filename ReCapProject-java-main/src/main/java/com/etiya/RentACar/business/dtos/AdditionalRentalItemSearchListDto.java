package com.etiya.RentACar.business.dtos;

import com.etiya.RentACar.entites.AdditionalService;
import com.etiya.RentACar.entites.Rental;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalRentalItemSearchListDto {

    private int additionalServiceId;

    private int rentalRentalId;
}
