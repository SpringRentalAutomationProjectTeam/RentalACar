package com.etiya.RentACar.business.requests.city;

import com.etiya.RentACar.entites.Car;
import com.etiya.RentACar.entites.Rental;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCityRequest {

    @NotNull
    private String cityName;
}
