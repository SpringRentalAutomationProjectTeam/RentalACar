package com.etiya.RentACar.ws;

import com.etiya.RentACar.business.abstracts.CityService;
import com.etiya.RentACar.business.dtos.CitySearchListDto;
import com.etiya.RentACar.business.requests.car.CreateCarRequest;
import com.etiya.RentACar.business.requests.city.CreateCityRequest;
import com.etiya.RentACar.business.requests.city.DeleteCityRequest;
import com.etiya.RentACar.business.requests.city.UpdateCityRequest;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/cities/")
public class CitiesController {

    private CityService cityService;

    @Autowired
    public CitiesController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("getAll")
    public DataResult<List<CitySearchListDto>> getAll(){
        return cityService.getAll();
    }

    @PostMapping("add")
    public Result add(@RequestBody @Valid CreateCityRequest createCityRequest) {
        return this.cityService.add(createCityRequest);
    }

    @PutMapping("update")
    public Result update(@RequestBody @Valid UpdateCityRequest updateCityRequest){
        return this.cityService.update(updateCityRequest);
    }

    @DeleteMapping("delete")
    public Result delete(@RequestBody @Valid DeleteCityRequest deleteCityRequest){
        return this.cityService.delete(deleteCityRequest);
    }
}
