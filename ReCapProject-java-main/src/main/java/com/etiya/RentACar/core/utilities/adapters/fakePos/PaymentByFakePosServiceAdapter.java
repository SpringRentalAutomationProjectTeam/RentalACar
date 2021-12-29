package com.etiya.RentACar.core.utilities.adapters.fakePos;

import com.etiya.RentACar.business.requests.PosServiceRequest;
import com.etiya.RentACar.outServices.FakePosService;
import org.springframework.stereotype.Service;

@Service
public class PaymentByFakePosServiceAdapter implements PaymentByFakePosService {


    FakePosService fakePosService = new FakePosService();
    @Override
    public  boolean withdraw(PosServiceRequest posServiceRequest){

    return fakePosService.isEnoughLimit(posServiceRequest.getCreditCardNumber(),posServiceRequest.getCvv()
    ,posServiceRequest.getExpirationDate(),posServiceRequest.getTotalAmount()) ;
    }

}
