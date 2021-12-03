package com.etiya.RentACar.core.utilities.adapters.fakePos;

import com.etiya.RentACar.business.requests.PosServiceRequest;

public interface PaymentByFakePosService {
     boolean withdraw(PosServiceRequest posServiceRequest);
}
