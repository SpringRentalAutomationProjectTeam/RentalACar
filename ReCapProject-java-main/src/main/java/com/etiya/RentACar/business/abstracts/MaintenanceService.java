package com.etiya.RentACar.business.abstracts;

import java.util.List;

import com.etiya.RentACar.business.dtos.MaintenanceDto;
import com.etiya.RentACar.business.dtos.MaintenanceSearchListDto;
import com.etiya.RentACar.business.requests.maintenance.CreateMaintenanceRequest;
import com.etiya.RentACar.business.requests.maintenance.DeleteMaintenanceRequest;
import com.etiya.RentACar.business.requests.maintenance.UpdateMaintenanceRequest;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;

public interface MaintenanceService {

	DataResult<List<MaintenanceSearchListDto>> getAll();
	Result add(CreateMaintenanceRequest createMaintenanceRequest);
	Result update(UpdateMaintenanceRequest updateMaintenanceRequest);
	Result delete(DeleteMaintenanceRequest deleteMaintenanceRequest);
	DataResult<MaintenanceSearchListDto> getById(int maintenanceId);
	Result checkIfCarIsMaintenance(int carId);
}
