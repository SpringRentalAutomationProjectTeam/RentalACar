package com.etiya.RentACar.business.concretes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import antlr.debug.MessageAdapter;
import com.etiya.RentACar.business.abstracts.LanguageWordService;
import com.etiya.RentACar.business.constants.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.etiya.RentACar.business.abstracts.CarImageService;
import com.etiya.RentACar.business.abstracts.CarService;
import com.etiya.RentACar.business.constants.FilePathConfiguration;
import com.etiya.RentACar.business.dtos.CarImagesDto;
import com.etiya.RentACar.business.dtos.CarImagesSearchListDto;
import com.etiya.RentACar.business.requests.carImages.CreateCarImageRequest;
import com.etiya.RentACar.business.requests.carImages.DeleteCarImagesRequest;
import com.etiya.RentACar.business.requests.carImages.UpdateCarImageRequest;
import com.etiya.RentACar.core.utilities.business.BusinessRules;
import com.etiya.RentACar.core.utilities.helpers.FileHelper;
import com.etiya.RentACar.core.utilities.mapping.ModelMapperService;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.ErrorDataResult;
import com.etiya.RentACar.core.utilities.results.ErrorResult;
import com.etiya.RentACar.core.utilities.results.Result;
import com.etiya.RentACar.core.utilities.results.SuccessDataResult;
import com.etiya.RentACar.core.utilities.results.SuccessResult;
import com.etiya.RentACar.dataAccess.abstracts.CarImageDao;
import com.etiya.RentACar.entites.CarImage;

@Service
public class CarImageManager implements CarImageService {

    private CarImageDao carImageDao;
    private ModelMapperService modelMapperService;
    private CarService carService;
    private FileHelper fileHelper;
    private LanguageWordService languageWordService;
    private Environment environment;


    @Autowired
    private CarImageManager(CarImageDao carImageDao, ModelMapperService modelMapperService,
                            @Lazy CarService carService, FileHelper fileHelper
            ,Environment environment, LanguageWordService languageWordService) {
        this.carImageDao = carImageDao;
        this.modelMapperService = modelMapperService;
        this.carService = carService;
        this.fileHelper = fileHelper;
        this.languageWordService = languageWordService;
        this.environment=environment;
    }

    @Override
    public DataResult<List<CarImagesSearchListDto>> getAll() {
        List<CarImage> carImages = this.carImageDao.findAll();
        List<CarImagesSearchListDto> result = carImages.stream()
                .map(carImage -> modelMapperService.forDto().map(carImage, CarImagesSearchListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<List<CarImagesSearchListDto>>(result, this.languageWordService.getValueByKey(Messages.CARIMAGELIST).getData());
    }

    @Override
    public DataResult<List<CarImagesDto>> getCarImageByCarId(int carId) {
        Result resultcheck = BusinessRules.run(checkIfCarExists(carId));
        if (resultcheck != null) {
            return new ErrorDataResult<List<CarImagesDto>>(null, this.languageWordService.getValueByKey(Messages.CARNOTFOUND).getData());
        }

        List<CarImage> carImages = this.checkIfCarImageExists(carId).getData();
        List<CarImagesDto> result = carImages.stream()
                .map(carImage -> modelMapperService.forDto().map(carImage, CarImagesDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult(result, this.languageWordService.getValueByKey(Messages.CARIMAGELIST).getData());
    }

    @Override
    public Result add(CreateCarImageRequest createCarImageRequest) throws IOException {
        Result result = BusinessRules.run(checkIfCarExists(createCarImageRequest.getCarId()),
                checkCountOfCarImages(createCarImageRequest.getCarId()));
        if (result != null) {
            return result;
        }

        CarImage carImage = modelMapperService.forRequest().map(createCarImageRequest, CarImage.class); //
        carImage.setImageDate(LocalDate.now());

        carImage.setImagePath(generateImage(createCarImageRequest.getFile()).toString());
        this.carImageDao.save(carImage);
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.CARIMAGEADD).getData());
    }

    @Override
    public Result update(UpdateCarImageRequest updateCarImageRequest) throws IOException {
        Result result = BusinessRules.run(checkIfImageExists(updateCarImageRequest.getImageId()),
                checkIfCarExists(updateCarImageRequest.getCarId()));
        if (result != null) {
            return result;
        }

        CarImage carImage = modelMapperService.forRequest().map(updateCarImageRequest, CarImage.class); //
        carImage.setImageDate(LocalDate.now());
        carImage.setImagePath(generateImage(updateCarImageRequest.getFile()).toString());
        this.carImageDao.save(carImage);
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.CARIMAGEUPDATE).getData());
    }

    @Override
    public Result delete(DeleteCarImagesRequest deleteCarImagesRequest) {
        Result result = BusinessRules.run(
                checkIfImageExists(deleteCarImagesRequest.getImageId()));
        if (result != null) {
            return result;
        }

        CarImage carImage = this.carImageDao.getById(deleteCarImagesRequest.getImageId());
        fileHelper.deleteImage(carImage.getImagePath());
        this.carImageDao.delete(carImage);
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.CARIMAGEDELETE).getData());
    }

    private File generateImage(MultipartFile file) throws IOException {
        String imagePathGuid = java.util.UUID.randomUUID().toString();

        File imageFile = new File(environment.getProperty("main.path")  + imagePathGuid + "."
                + file.getContentType().substring(file.getContentType().indexOf("/") + 1));

        imageFile.createNewFile();
        FileOutputStream outputImage = new FileOutputStream(imageFile);
        outputImage.write(file.getBytes());
        outputImage.close();

        return imageFile;
    }

    private DataResult<List<CarImage>> checkIfCarImageExists(int carId) {

        if (this.carImageDao.existsByCar_CarId(carId)) {
            List<CarImage> result = this.carImageDao.getByCar_CarId(carId);
            return new SuccessDataResult<List<CarImage>>(result);
        }
        List<CarImage> carImages = new ArrayList<CarImage>();
        CarImage carImage = new CarImage();
        carImage.setImagePath(environment.getProperty("main.path") + environment.getProperty("default.name"));
        carImages.add(carImage);
        return new SuccessDataResult<List<CarImage>>(carImages, this.languageWordService.getValueByKey(Messages.CARIMAGELIST).getData());
    }


    private Result checkCountOfCarImages(int carId) {
        if (this.carImageDao.countCarImageByCar_CarId(carId) >= 5) {
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.CARIMAGELIMITERROR).getData());
        }
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.CARIMAGELIMIT).getData());
    }


    private Result checkIfImageExists(int imageId) {
        if (!this.carImageDao.existsById(imageId)) {
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.CARIMAGENOTFOUND).getData());
        }
        return new SuccessResult();
    }

    private Result checkIfCarExists(int carId) {

        if (!this.carService.checkIfCarExists(carId).isSuccess()) {
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.CARNOTFOUND).getData());
        }
        return new SuccessResult();
    }
}
