package com.etiya.RentACar.ws;

import com.etiya.RentACar.business.abstracts.AdditionalRentalItemService;
import com.etiya.RentACar.business.dtos.AdditionalRentalItemSearchListDto;
import com.etiya.RentACar.business.requests.additionalRentalItem.CreateAdditionalRentalItemRequest;
import com.etiya.RentACar.business.requests.additionalRentalItem.DeleteAdditionalRentalItemRequest;
import com.etiya.RentACar.business.requests.additionalRentalItem.UpdateAdditionalRentalItemRequest;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/additionalRentalItems/")
public class AdditionalRentalItemController {

    private AdditionalRentalItemService additionalRentalItemService;

    @Autowired
    public AdditionalRentalItemController(AdditionalRentalItemService additionalRentalItemService){
        this.additionalRentalItemService = additionalRentalItemService;
    }

    @GetMapping("getAll")
    public DataResult<List<AdditionalRentalItemSearchListDto>> getAll(){
        return this.additionalRentalItemService.getAll();
    }

    @GetMapping("getByRentalId")
    public DataResult<List<AdditionalRentalItemSearchListDto>> getByRentalId(int id){
        return this.additionalRentalItemService.getByRentalId(id);
    }


    @PostMapping("add")
    public Result add(@RequestBody @Valid CreateAdditionalRentalItemRequest createAdditionalRentalItemRequest){
        return this.additionalRentalItemService.add(createAdditionalRentalItemRequest);
    }

    @DeleteMapping("delete")
    public Result delete(@RequestBody @Valid DeleteAdditionalRentalItemRequest deleteAdditionalRentalItemRequest){
        return  this.additionalRentalItemService.delete(deleteAdditionalRentalItemRequest);
    }

    @PutMapping("update")
    public Result update(@RequestBody @Valid UpdateAdditionalRentalItemRequest updateAdditionalRentalItemRequest){
        return this.additionalRentalItemService.update(updateAdditionalRentalItemRequest);
    }
}
