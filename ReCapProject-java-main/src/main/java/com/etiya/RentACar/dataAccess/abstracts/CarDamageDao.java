package com.etiya.RentACar.dataAccess.abstracts;

import com.etiya.RentACar.entites.Car;
import com.etiya.RentACar.entites.CarDamage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarDamageDao extends JpaRepository<CarDamage,Integer> {

    List<CarDamage> getByCar_CarId(int carId);
}
