package com.RoieIvri.CouponsPhase2.CUSTOMER;

public class CustomerException extends Exception{
    public CustomerException(CustomerExceptionTypes message){
        super(message.toString());
    }
}
