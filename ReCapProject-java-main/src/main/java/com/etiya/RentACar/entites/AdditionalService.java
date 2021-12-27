package com.etiya.RentACar.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="additional_services")
public class AdditionalService {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "service_id")
    private int serviceId;

    @Column(name = "service_name")
    private String serviceName;


    @Column(name = "service_daily_price")
    private int serviceDailyPrice;

    @OneToMany(mappedBy = "additionalService")
    private List<AdditionalRentalItem> additionalRentalItems;

}
