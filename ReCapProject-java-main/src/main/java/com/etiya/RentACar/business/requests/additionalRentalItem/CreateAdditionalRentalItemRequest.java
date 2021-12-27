package com.etiya.RentACar.business.requests.additionalRentalItem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdditionalRentalItemRequest {
    @JsonIgnore
    private int id;

    private  int additionalServiceId;

    private int rentalId;
}
