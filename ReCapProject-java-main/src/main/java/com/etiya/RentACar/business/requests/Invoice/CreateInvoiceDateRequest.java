package com.etiya.RentACar.business.requests.Invoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceDateRequest {
    private LocalDate minDate;
    private LocalDate maxDate;
}
