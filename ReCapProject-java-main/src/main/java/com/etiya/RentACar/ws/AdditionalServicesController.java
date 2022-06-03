package com.etiya.RentACar.ws;

import com.etiya.RentACar.business.abstracts.RentalAdditionalService;
import com.etiya.RentACar.business.dtos.AdditionalServiceSearchListDto;
import com.etiya.RentACar.business.requests.additionalService.CreateAdditionalServiceRequest;
import com.etiya.RentACar.business.requests.additionalService.DeleteAdditionalServiceRequest;
import com.etiya.RentACar.business.requests.additionalService.UpdateAdditionalServiceRequest;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/additionalservices/")
public class AdditionalServicesController {

    private RentalAdditionalService rentalAdditionalService;

    @Autowired
    public AdditionalServicesController(RentalAdditionalService rentalAdditionalService){
        super();
        this.rentalAdditionalService = rentalAdditionalService;
    }

    @GetMapping("getAll")
    public DataResult<List<AdditionalServiceSearchListDto>> getAll() {
        return this.rentalAdditionalService.getAll();
    }

    @PostMapping("add")
    public Result add(@RequestBody CreateAdditionalServiceRequest createAdditionalServiceRequest) {
        return this.rentalAdditionalService.add(createAdditionalServiceRequest);
    }

    @PutMapping("update")
    public Result update(@RequestBody UpdateAdditionalServiceRequest updateAdditionalServiceRequest) {
        return this.rentalAdditionalService.update(updateAdditionalServiceRequest);
    }

    @DeleteMapping("delete")
    public Result delete(@RequestBody DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) {
        return this.rentalAdditionalService.delete(deleteAdditionalServiceRequest);
    }

}
