package com.etiya.RentACar.business.concretes;

import java.util.List;

import com.etiya.RentACar.business.abstracts.LanguageWordService;
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
    private LanguageWordService languageWordService;

    @Autowired
    private ColorManager(ColorDao colorDao, ModelMapperService modelMapperService, @Lazy CarService carService
                        ,LanguageWordService languageWordService) {
        this.colorDao = colorDao;
        this.modelMapperService = modelMapperService;
        this.carService = carService;
        this.languageWordService = languageWordService;
    }

    @Override
    public DataResult<List<Color>> getAll() {
        return new SuccessDataResult<List<Color>>(this.colorDao.findAll(),this.languageWordService.getValueByKey(Messages.COLORLIST).getData());
    }

    @Override
    public Result add(CreateColorRequest createColorRequest) {
        Result result = BusinessRules.run(checkIfColorNameExists(createColorRequest.getColorName()));
        if (result != null) {
            return result;
        }

        Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);
        this.colorDao.save(color);
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.COLORADD).getData());
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
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.COLORUPDATE).getData());
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
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.COLORDELETE).getData());
    }

    private Result checkIfExistsColorInCar(int colorId) {
        if (this.carService.checkIfExistsColorInCar(colorId).isSuccess()) {
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.COLORDELETEERROR).getData());
        }
        return new SuccessResult();
    }

    @Override
    public Result checkIfColorExists(int colorId) {
        if (!this.colorDao.existsById(colorId)) {
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.COLORNOTFOUND).getData());
        }
        return new SuccessResult();
    }

    private Result checkIfColorNameExists(String colorName) {
        if (this.colorDao.existsByColorName(colorName)) {
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.COLORNAMEERROR).getData());
        }
        return new SuccessResult();
    }

}
