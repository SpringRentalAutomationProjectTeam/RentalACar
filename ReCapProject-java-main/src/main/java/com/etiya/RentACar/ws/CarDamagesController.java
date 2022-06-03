package com.etiya.RentACar.ws;

import com.etiya.RentACar.business.abstracts.CarDamageService;
import com.etiya.RentACar.business.dtos.CarDamageSearchListDto;
import com.etiya.RentACar.business.requests.carDamage.CreateCarDamageRequest;
import com.etiya.RentACar.business.requests.carDamage.DeleteCarDamageRequest;
import com.etiya.RentACar.business.requests.carDamage.UpdateCarDamageRequest;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/cardamages/")
public class CarDamagesController {

    private CarDamageService carDamageService;

    @Autowired
    public CarDamagesController(CarDamageService carDamageService) {
        this.carDamageService = carDamageService;
    }

    @GetMapping("getAll")
    public DataResult<List<CarDamageSearchListDto>> getAll(){
        return this.carDamageService.getAllDamages();
    }

    @GetMapping("getCarDamageInformationsByCarId")
    public DataResult<List<CarDamageSearchListDto>> getCarDamageInformationsByCarId(@RequestParam("carId") int carId){
        return this.carDamageService.getDamagesByCarId(carId);
    }

    @PostMapping("add")
    public Result add(@RequestBody @Valid CreateCarDamageRequest createCarDamageRequest){
        return this.carDamageService.add(createCarDamageRequest);
    }

    @PutMapping("update")
    public Result update(@RequestBody @Valid UpdateCarDamageRequest updateCarDamageRequest){
        return this.carDamageService.update(updateCarDamageRequest);
    }

    @DeleteMapping("delete")
    public Result delete(@RequestBody @Valid DeleteCarDamageRequest deleteCarDamageRequest){
        return this.carDamageService.delete(deleteCarDamageRequest);
    }

}
