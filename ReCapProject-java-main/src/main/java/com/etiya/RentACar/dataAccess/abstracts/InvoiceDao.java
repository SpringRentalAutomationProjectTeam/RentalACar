package com.etiya.RentACar.dataAccess.abstracts;

import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.RentACar.entites.Invoice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface InvoiceDao extends JpaRepository<Invoice, Integer> {
    List<Invoice> getByRental_User_Id(int userId);

    boolean existsByRental_RentalId(int rentalId);

    boolean existsByRental_UserId(int userId);
    List<Invoice> findByInvoiceDateBetween(LocalDate beginDate, LocalDate endDate);

}
