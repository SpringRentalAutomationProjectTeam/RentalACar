package com.etiya.RentACar.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="car_damages")
public class CarDamage {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_damage_id")
    private int carDamageId;

    @ManyToOne
    @JoinColumn(name="id")
    private Car car;

    @Column(name = "damage_information")
    private String damageInformation;
}
