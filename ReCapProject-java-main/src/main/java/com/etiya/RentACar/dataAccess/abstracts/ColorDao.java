package com.etiya.RentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.RentACar.entites.Color;

public interface ColorDao extends JpaRepository<Color, Integer> {

	boolean existsById(int colorId);
	boolean existsByColorName(String colorName);
}
