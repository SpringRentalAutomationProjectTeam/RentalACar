package com.etiya.RentACar.business.requests.MessageKey;

import com.etiya.RentACar.entites.LanguageWord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMessageKeyRequest {
    private String key;

}
