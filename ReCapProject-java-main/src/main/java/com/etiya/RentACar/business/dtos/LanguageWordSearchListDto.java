package com.etiya.RentACar.business.dtos;

import com.etiya.RentACar.entites.Language;
import com.etiya.RentACar.entites.MessageKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanguageWordSearchListDto {


    private int messageKeyId;

    private int languageId;

    private String translation;

}
