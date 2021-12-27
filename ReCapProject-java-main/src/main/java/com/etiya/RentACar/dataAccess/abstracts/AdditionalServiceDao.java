package com.etiya.RentACar.dataAccess.abstracts;

import com.etiya.RentACar.business.dtos.AdditionalServiceSearchListDto;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.entites.AdditionalService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdditionalServiceDao extends JpaRepository<AdditionalService,Integer> {
    boolean existsByServiceName(String serviceName);

    DataResult<AdditionalServiceSearchListDto> getByServiceId(int serviceId);

}
