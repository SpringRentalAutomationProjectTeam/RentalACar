package com.etiya.RentACar.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.etiya.RentACar.business.abstracts.BrandService;
import com.etiya.RentACar.business.abstracts.CarService;
import com.etiya.RentACar.business.requests.brand.CreateBrandRequest;
import com.etiya.RentACar.business.requests.brand.DeleteBrandRequest;
import com.etiya.RentACar.business.requests.brand.UpdateBrandRequest;
import com.etiya.RentACar.core.utilities.business.BusinessRules;
import com.etiya.RentACar.core.utilities.mapping.ModelMapperService;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.ErrorResult;
import com.etiya.RentACar.core.utilities.results.Result;
import com.etiya.RentACar.core.utilities.results.SuccessDataResult;
import com.etiya.RentACar.core.utilities.results.SuccessResult;
import com.etiya.RentACar.dataAccess.abstracts.BrandDao;
import com.etiya.RentACar.entites.Brand;

@Service
public class BrandManager implements BrandService {

	private BrandDao brandDao;
	private ModelMapperService modelMapperService;
	private CarService carService;

	@Autowired
	private BrandManager(BrandDao brandDao, ModelMapperService modelMapperService, @Lazy CarService carService) {
		super();
		this.brandDao = brandDao;
		this.modelMapperService = modelMapperService;
		this.carService = carService;
	}

	@Override
	public DataResult<List<Brand>> getAll() {
		
		return new SuccessDataResult<List<Brand>>(this.brandDao.findAll(), "Markalar listelendi");
	}

	@Override
	public Result add(CreateBrandRequest createBrandRequest) {
		Result result = BusinessRules.run(this.checkBrandByBrandName(createBrandRequest.getBrandName()));
		if (result != null) {
			return result;
		}
		Brand brand2 = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
		this.brandDao.save(brand2);
		return new SuccessResult();
	}

	@Override
	public Result update(UpdateBrandRequest updateBrandRequest) {
		Result result = BusinessRules.run(existsByBrand_Id(updateBrandRequest.getBrandId()));

		if (result != null) {
			return result;
		}
		Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);
		this.brandDao.save(brand);
		return new SuccessResult();

	}

	@Override
	public Result delete(DeleteBrandRequest deleteBrandRequest) {
		Result result = BusinessRules.run(existsByBrand_Id(deleteBrandRequest.getBrandId())
				,checkIfExistsBrandIdInCar(deleteBrandRequest.getBrandId()));

		if (result != null) {
			return result;
		}
		Brand brand = this.modelMapperService.forRequest().map(deleteBrandRequest, Brand.class);
		this.brandDao.delete(brand);
		return new SuccessResult();
	}

	private Result checkIfExistsBrandIdInCar(int brandId) {		
		if (this.carService.checkIfExistsBrandIdInCar(brandId).isSuccess()) {
			return new ErrorResult("Bu markaya ait araba bulundu. Önce arabaları silmelisiniz.");
		}
		return new SuccessResult();
	
}
	
	private Result checkBrandByBrandName(String brandName) {

		if (this.brandDao.existsByBrandName(brandName)) {
			return new ErrorResult("Bu Marka Mevcut");
		}
		return new SuccessResult();
	}

	@Override
	public Result existsByBrand_Id(int brandId) {

		if (!this.brandDao.existsById(brandId)) {
			return new ErrorResult("Brand Bulunamadı");
		}
		return new SuccessResult();
	}

}
