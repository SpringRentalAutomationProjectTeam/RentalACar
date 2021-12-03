package com.etiya.RentACar.outServices;


public class FakePosService {
    public boolean isEnoughLimit(String cardNumber , String cvv , double totalAmount){

        int limit=20000;
        if (totalAmount<=limit){
            return true;
        }

        return false;
    }

}
