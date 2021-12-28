package com.etiya.RentACar.business.requests.LanguageWord;

import com.etiya.RentACar.entites.Language;
import com.etiya.RentACar.entites.MessageKey;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLanguageWordRequest {

    @JsonIgnore
    private int id;

    private int messageKeyId;

    private int languageId;

    private String translation;
}
