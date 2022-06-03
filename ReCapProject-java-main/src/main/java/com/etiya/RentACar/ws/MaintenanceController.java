package com.etiya.RentACar.ws;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.RentACar.business.abstracts.MaintenanceService;
import com.etiya.RentACar.business.dtos.MaintenanceDto;
import com.etiya.RentACar.business.dtos.MaintenanceSearchListDto;
import com.etiya.RentACar.business.requests.maintenance.CreateMaintenanceRequest;
import com.etiya.RentACar.business.requests.maintenance.DeleteMaintenanceRequest;
import com.etiya.RentACar.business.requests.maintenance.UpdateMaintenanceRequest;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;
import com.etiya.RentACar.core.utilities.results.SuccessDataResult;

@RestController
@RequestMapping("api/maintenance/")
public class MaintenanceController {

	private MaintenanceService maintenanceService;

	@Autowired
	public MaintenanceController(MaintenanceService maintenanceService) {
		super();
		this.maintenanceService = maintenanceService;
	}
	
	@GetMapping("all")
	public DataResult<List<MaintenanceSearchListDto>> getAllDetail (){
		return this.maintenanceService.getAll(); 
	}
	
	@GetMapping("getById")
	public DataResult<MaintenanceSearchListDto> getById( int id){
		return this.maintenanceService.getById(id);
	}
	
	@PostMapping("add")
	public Result add(@RequestBody CreateMaintenanceRequest createMaintenanceRequest) {
		return this.maintenanceService.add(createMaintenanceRequest);
	}
	@PutMapping("update")
	public Result update(@RequestBody UpdateMaintenanceRequest updateMaintenanceRequest) {
		return this.maintenanceService.update(updateMaintenanceRequest);
	}
	@DeleteMapping("delete")
	public Result delete(@RequestBody DeleteMaintenanceRequest deleteMaintenanceRequest) {
		return this.maintenanceService.delete(deleteMaintenanceRequest);
	}
}
