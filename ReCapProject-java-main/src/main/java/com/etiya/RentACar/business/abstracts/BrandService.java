package com.etiya.RentACar.business.abstracts;

import java.util.List;

import com.etiya.RentACar.business.requests.brand.CreateBrandRequest;
import com.etiya.RentACar.business.requests.brand.DeleteBrandRequest;
import com.etiya.RentACar.business.requests.brand.UpdateBrandRequest;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;
import com.etiya.RentACar.entites.Brand;

public interface BrandService {
	DataResult<List<Brand>> getAll();
	Result add(CreateBrandRequest createBrandRequest);
	Result update(UpdateBrandRequest updateBrandRequest);
	Result delete(DeleteBrandRequest deleteBrandRequest);
	Result checkIfBrandExists(int brandId);
}
