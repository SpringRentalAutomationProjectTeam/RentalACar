package com.etiya.RentACar.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.etiya.RentACar.business.abstracts.CarService;
import com.etiya.RentACar.business.abstracts.ColorService;
import com.etiya.RentACar.business.constants.Messages;
import com.etiya.RentACar.business.requests.color.CreateColorRequest;
import com.etiya.RentACar.business.requests.color.DeleteColorRequest;
import com.etiya.RentACar.business.requests.color.UpdateColorRequest;
import com.etiya.RentACar.core.utilities.business.BusinessRules;
import com.etiya.RentACar.core.utilities.mapping.ModelMapperService;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.ErrorDataResult;
import com.etiya.RentACar.core.utilities.results.ErrorResult;
import com.etiya.RentACar.core.utilities.results.Result;
import com.etiya.RentACar.core.utilities.results.SuccessDataResult;
import com.etiya.RentACar.core.utilities.results.SuccessResult;
import com.etiya.RentACar.dataAccess.abstracts.ColorDao;
import com.etiya.RentACar.entites.Color;

@Service
public class ColorManager implements ColorService {

    private ColorDao colorDao;
    private ModelMapperService modelMapperService;
    private CarService carService;

    @Autowired
    private ColorManager(ColorDao colorDao, ModelMapperService modelMapperService, @Lazy CarService carService) {
        super();
        this.colorDao = colorDao;
        this.modelMapperService = modelMapperService;
        this.carService = carService;
    }

    @Override
    public DataResult<List<Color>> getAll() {
        return new SuccessDataResult<List<Color>>(this.colorDao.findAll(),Messages.COLORLIST);
    }

    @Override
    public Result add(CreateColorRequest createColorRequest) {
        Result result = BusinessRules.run(checkIfColorNameExists(createColorRequest.getColorName()));
        if (result != null) {
            return result;
        }

        Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);
        this.colorDao.save(color);
        return new SuccessResult(Messages.COLORADD);
    }

    @Override
    public Result update(UpdateColorRequest updateColorRequest) {
        Result result = BusinessRules.run(checkIfColorNameExists(updateColorRequest.getColorName()),
                checkIfColorExists(updateColorRequest.getColorId()));
        if (result != null) {
            return result;
        }

        Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);
        this.colorDao.save(color);
        return new SuccessResult(Messages.COLORUPDATE);
    }

    @Override
    public Result delete(DeleteColorRequest deleteColorRequest) {
        Result result = BusinessRules.run(checkIfColorExists(deleteColorRequest.getColorId())
                , checkIfExistsColorInCar(deleteColorRequest.getColorId()));
        if (result != null) {
            return result;
        }

        Color color = this.modelMapperService.forRequest().map(deleteColorRequest, Color.class);
        this.colorDao.delete(color);
        return new SuccessResult(Messages.COLORDELETE);
    }

    private Result checkIfExistsColorInCar(int colorId) {
        if (this.carService.checkIfExistsColorInCar(colorId).isSuccess()) {
            return new ErrorResult(Messages.COLORDELETEERROR);
        }
        return new SuccessResult();
    }

    @Override
    public Result checkIfColorExists(int colorId) {
        if (!this.colorDao.existsById(colorId)) {
            return new ErrorResult(Messages.COLORNOTFOUND);
        }
        return new SuccessResult();
    }

    private Result checkIfColorNameExists(String colorName) {
        if (this.colorDao.existsByColorName(colorName)) {
            return new ErrorResult(Messages.COLORNAMEERROR);
        }
        return new SuccessResult();
    }

}
