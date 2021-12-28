package com.etiya.RentACar.business.requests.brand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBrandRequest {
	@NotNull
	private int brandId;
	@NotNull
	private String brandName;
}
