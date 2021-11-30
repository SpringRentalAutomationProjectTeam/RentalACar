package com.etiya.RentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.RentACar.entites.Invoice;

public interface InvoiceDao extends JpaRepository<Invoice, Integer>{

}
