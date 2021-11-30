package com.etiya.RentACar.business.requests.Invoice;


import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteInvoiceRequest {
	
	@NotNull
	private int invoiceId;
	
}
