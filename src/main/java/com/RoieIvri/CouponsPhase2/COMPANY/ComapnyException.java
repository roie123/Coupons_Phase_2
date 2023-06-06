package com.RoieIvri.CouponsPhase2.COMPANY;

public class ComapnyException extends Exception{






    public ComapnyException(CompanyExceptionTypes companyExceptionTypes){
        super(companyExceptionTypes.toString());
    }

    String clientValue;


}
