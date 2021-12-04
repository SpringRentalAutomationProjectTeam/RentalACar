package com.etiya.RentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.RentACar.entites.CorporateCustomer;

public interface CorporateCustomersDao extends JpaRepository<CorporateCustomer, Integer>{

    boolean existsCorporateCustomerByCompanyName(String companyName);

}
