package com.etiya.RentACar.entites;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="additional_rental_item")
public class AdditionalRentalItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "additional_service_id")
    private AdditionalService additionalService;

    @ManyToOne
    @JoinColumn(name = "rental_id")
    private Rental rental;
}
