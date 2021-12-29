package com.etiya.RentACar.business.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PosServiceRequest {

    private String creditCardNumber;
    private String cvv;
    private String expirationDate;
    private double totalAmount;
}
