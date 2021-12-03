package com.etiya.RentACar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalServiceSearchListDto {
    private int serviceId;

    private String serviceName;

    private int serviceDailyPrice;
}
