package com.etiya.RentACar.business.requests.Rental;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentalRequest {

    @JsonIgnore
    private int id;
    @NotNull
    private int carId;
    @NotNull
    private int userId;
    @NotNull
    private LocalDate rentDate;
    @NotNull
    private String startKm;

    @NotNull
    private int rentCityId;


}
