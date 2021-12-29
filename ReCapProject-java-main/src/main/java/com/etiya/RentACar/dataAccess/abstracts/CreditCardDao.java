package com.etiya.RentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.RentACar.entites.CreditCard;

public interface CreditCardDao extends JpaRepository<CreditCard, Integer>{
	boolean existsByCardNumber(String cardNumber);

	boolean existsById(int creditCardId);
	boolean existsByUserId(int userId);
}
