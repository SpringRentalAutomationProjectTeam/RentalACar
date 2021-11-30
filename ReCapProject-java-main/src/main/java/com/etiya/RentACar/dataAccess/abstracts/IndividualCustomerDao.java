package com.etiya.RentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.RentACar.entites.IndividualCustomer;

public interface IndividualCustomerDao  extends JpaRepository<IndividualCustomer, Integer>{

}
