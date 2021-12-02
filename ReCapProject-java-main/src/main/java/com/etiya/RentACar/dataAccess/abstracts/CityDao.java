package com.etiya.RentACar.dataAccess.abstracts;

import com.etiya.RentACar.entites.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityDao extends JpaRepository<City,Integer> {


    boolean existsByCityName(String cityName);

}
