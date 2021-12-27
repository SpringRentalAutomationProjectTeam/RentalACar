package com.etiya.RentACar.business.requests.Rental;

import java.time.LocalDate;
import java.util.List;

import com.etiya.RentACar.business.requests.creditCard.CreateCreditCardRequest;
import com.etiya.RentACar.entites.CreditCard;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentalRequest  {

    @JsonIgnore
    private int id;
    @NotNull
    private int carId;
    @NotNull
    private int userId;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate rentDate;
    @NotNull
    private String startKm;

    @NotNull
    private int rentCityId;


}
