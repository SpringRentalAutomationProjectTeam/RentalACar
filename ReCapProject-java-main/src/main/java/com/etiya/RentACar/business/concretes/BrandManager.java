package com.etiya.RentACar.business.concretes;

import java.util.List;

import com.etiya.RentACar.business.constants.Messages;
import org.aspectj.bridge.Message;
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
        return new SuccessDataResult<List<Brand>>(this.brandDao.findAll(), Messages.BRANDLIST);
    }

    @Override
    public Result add(CreateBrandRequest createBrandRequest) {
        Result result = BusinessRules.run(this.checkIfBrandNameExists(createBrandRequest.getBrandName()));
        if (result != null) {
            return result;
        }

        Brand brand2 = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
        this.brandDao.save(brand2);
        return new SuccessResult(Messages.BRANDADD);
    }

    @Override
    public Result update(UpdateBrandRequest updateBrandRequest) {
        Result result = BusinessRules.run(checkIfBrandExists(updateBrandRequest.getBrandId()));
        if (result != null) {
            return result;
        }

        Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);
        this.brandDao.save(brand);
        return new SuccessResult(Messages.BRANDUPDATE);
    }

    @Override
    public Result delete(DeleteBrandRequest deleteBrandRequest) {
        Result result = BusinessRules.run(checkIfBrandExists(deleteBrandRequest.getBrandId())
                , checkIfIsThereCarOfThisBrand(deleteBrandRequest.getBrandId()));
        if (result != null) {
            return result;
        }

        Brand brand = this.modelMapperService.forRequest().map(deleteBrandRequest, Brand.class);
        this.brandDao.delete(brand);
        return new SuccessResult(Messages.BRANDDELETE);
    }

    @Override
    public Result checkIfBrandExists(int brandId) {
        if (!this.brandDao.existsById(brandId)) {
            return new ErrorResult(Messages.BRANDNOTFOUND);
        }
        return new SuccessResult(Messages.BRANDGET);
    }

    private Result checkIfIsThereCarOfThisBrand(int brandId) {
        if (this.carService.checkIfExistsBrandInCar(brandId).isSuccess()) {
            return new ErrorResult(Messages.BRANDDELETEERROR);
        }
        return new SuccessResult();
    }

    private Result checkIfBrandNameExists(String brandName) {
        if (this.brandDao.existsByBrandName(brandName)) {
            return new ErrorResult(Messages.BRANDNAMEERROR);
        }
        return new SuccessResult();
    }

}
