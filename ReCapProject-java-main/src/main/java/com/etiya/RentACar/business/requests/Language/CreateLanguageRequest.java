package com.etiya.RentACar.business.requests.Language;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLanguageRequest {
    @NotNull
    private String languageName;

}
