package com.etiya.RentACar.business.concretes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import antlr.debug.MessageAdapter;
import com.etiya.RentACar.business.constants.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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

    @Autowired
    private CarImageManager(CarImageDao carImageDao, ModelMapperService modelMapperService,
                            @Lazy CarService carService, FileHelper fileHelper) {
        super();
        this.carImageDao = carImageDao;
        this.modelMapperService = modelMapperService;
        this.carService = carService;
        this.fileHelper = fileHelper;
    }

    @Override
    public DataResult<List<CarImagesSearchListDto>> getAll() {
        List<CarImage> carImages = this.carImageDao.findAll();
        List<CarImagesSearchListDto> result = carImages.stream()
                .map(carImage -> modelMapperService.forDto().map(carImage, CarImagesSearchListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<List<CarImagesSearchListDto>>(result, Messages.CARIMAGELIST);
    }

    @Override
    public DataResult<List<CarImagesDto>> getCarImageByCarId(int carId) {
        Result resultcheck = BusinessRules.run(checkIfCarExists(carId));
        if (resultcheck != null) {
            return new ErrorDataResult<List<CarImagesDto>>(null, Messages.CARNOTFOUND);
        }

        List<CarImage> carImages = this.checkIfCarImageExists(carId).getData();
        List<CarImagesDto> result = carImages.stream()
                .map(carImage -> modelMapperService.forDto().map(carImage, CarImagesDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult(result, Messages.CARIMAGELIST);
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
        return new SuccessResult(Messages.CARIMAGEADD);
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
        return new SuccessResult(Messages.CARIMAGEUPDATE);
    }

    @Override
    public Result delete(DeleteCarImagesRequest deleteCarImagesRequest) {
        Result result = BusinessRules.run(checkIfImageExists(deleteCarImagesRequest.getImageId()));
        if (result != null) {
            return result;
        }

        CarImage carImage = this.carImageDao.getById(deleteCarImagesRequest.getImageId());
        fileHelper.deleteImage(carImage.getImagePath());
        this.carImageDao.delete(carImage);
        return new SuccessResult(Messages.CARIMAGEDELETE);
    }

    private File generateImage(MultipartFile file) throws IOException {
        String imagePathGuid = java.util.UUID.randomUUID().toString();
        File imageFile = new File(FilePathConfiguration.mainPath + imagePathGuid + "."
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
        carImage.setImagePath(FilePathConfiguration.mainPath + FilePathConfiguration.defaultImage);
        carImages.add(carImage);
        return new SuccessDataResult<List<CarImage>>(carImages, Messages.CARIMAGELIST);
    }


    private Result checkCountOfCarImages(int carId) {
        if (this.carImageDao.countCarImageByCar_CarId(carId) >= 5) {
            return new ErrorResult(Messages.CARIMAGELIMITERROR);
        }
        return new SuccessResult(Messages.CARIMAGELIMIT);
    }


    private Result checkIfImageExists(int imageId) {
        if (!this.carImageDao.existsById(imageId)) {
            return new ErrorResult(Messages.CARIMAGENOTFOUND);
        }
        return new SuccessResult(Messages.CARIMAGEGET);
    }

    private Result checkIfCarExists(int carId) {

        if (!this.carService.checkIfCarExists(carId).isSuccess()) {
            return new ErrorResult(Messages.CARNOTFOUND);
        }
        return new SuccessResult(Messages.CARFOUND);
    }
}
