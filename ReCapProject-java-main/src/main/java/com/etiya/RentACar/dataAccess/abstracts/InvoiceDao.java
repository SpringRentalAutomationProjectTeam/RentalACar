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

    List<Invoice> findByInvoiceDateBetween(LocalDate beginDate, LocalDate endDate);

/*
    @Query(value = "Select new com.etiya.RentACar.entites.Invoice " +
            "(i.invoiceId,i.invoiceNumber,i.invoiceDate,i.totalRentalDay,i.totalAmount) "
            + "From Invoice i where i.invoiceDate between :minDate and :maxDate")
    List<Invoice> getByCreationDateBetween(@Param("minDate") LocalDate startDate, @Param("maxDate") LocalDate endDate);
*/
}
