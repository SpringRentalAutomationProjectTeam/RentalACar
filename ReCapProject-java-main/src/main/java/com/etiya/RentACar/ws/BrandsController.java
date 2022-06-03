package com.etiya.RentACar.ws;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.RentACar.business.abstracts.BrandService;
import com.etiya.RentACar.business.requests.brand.CreateBrandRequest;
import com.etiya.RentACar.business.requests.brand.DeleteBrandRequest;
import com.etiya.RentACar.business.requests.brand.UpdateBrandRequest;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;
import com.etiya.RentACar.entites.Brand;

@RestController
@RequestMapping("api/brands/")
public class BrandsController {
	private BrandService brandService;

	@Autowired
	private BrandsController(BrandService brandService) {
		super();
		this.brandService = brandService;
	}

	@GetMapping("getAll")
	public DataResult<List<Brand>> getAll() {
		return brandService.getAll();
	}

	@PostMapping("add")
	public Result add(@RequestBody CreateBrandRequest createBrandRequest) {
		return this.brandService.add(createBrandRequest);
	}

	@PutMapping("update")
	public Result update(@RequestBody UpdateBrandRequest updateBrandRequest) {
		return this.brandService.update(updateBrandRequest);
	}

	@DeleteMapping("delete")
	public Result delete(@RequestBody DeleteBrandRequest deleteBrandRequest) {
		return this.brandService.delete(deleteBrandRequest);
	}

}
