package com.etiya.RentACar.dataAccess.abstracts;

import com.etiya.RentACar.entites.AdditionalRentalItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdditionalRentalItemDao extends JpaRepository<AdditionalRentalItem,Integer> {

    List<AdditionalRentalItem> getByRentalRentalId(int rentalId);

}
