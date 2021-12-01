package com.etiya.RentACar.business.requests.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceDateRequest {

    @JsonIgnore
    private int invoiceId;

    private int rentalId;

    private String invoiceNumber;

    @JsonIgnore
    private LocalDate invoiceDate;

    @JsonIgnore
    private int totalRentalDay;

    @JsonIgnore
    private double totalAmount;
}
