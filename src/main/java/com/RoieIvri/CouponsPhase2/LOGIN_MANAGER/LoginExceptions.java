package com.RoieIvri.CouponsPhase2.LOGIN_MANAGER;


public class LoginExceptions extends  Exception{


    public LoginExceptions(LoginExceptionTypes loginExceptionTypes){
        super(loginExceptionTypes.toString());

    }


}
