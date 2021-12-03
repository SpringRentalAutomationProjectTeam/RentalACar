package com.etiya.RentACar.dataAccess.abstracts;

import com.etiya.RentACar.entites.AdditionalService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdditionalServiceDao extends JpaRepository<AdditionalService,Integer> {
    boolean existsByServiceName(String serviceName);
}
