package com.etiya.RentACar.business.requests.MessageKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMessageKeyRequest {
    private int id;
    private String key;
}
