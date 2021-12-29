package com.etiya.RentACar.business.dtos;


import com.etiya.RentACar.business.constants.Messages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardRentalDto {



    @Pattern(regexp="(\\d{16})")
    @NotNull
    private String cardNumber;

    @NotNull
    private String expirationDate;

    @Pattern(regexp="(\\d{3})")
    @NotNull
    private String cvv;

}
