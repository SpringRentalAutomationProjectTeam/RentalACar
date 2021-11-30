package com.etiya.RentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.RentACar.entites.Brand;

public interface BrandDao extends JpaRepository<Brand, Integer> {
	
	boolean existsByBrandName(String brandName);

	boolean existsById(int brandId);

}
