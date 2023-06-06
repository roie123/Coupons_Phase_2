package com.RoieIvri.CouponsPhase2.COMPANY;

public enum CompanyExceptionTypes {
    COMPANY_NOT_FOUND_BY_ID("Company Was Not Found By Id"),
    CANNOT_ADD_NULL_COUPON("Cannot Add Null Coupon"),
    CANNOT_ADD_NULL_COMPANY("Cannot Add Null Company"),
    COMPANY_IS_NULL("Company Is Null"),
    COUPON_ALREADY_EXIST("Coupon Already Exists in This Company"),
    COMPANY_DONT_HAVE_THAT_COUPON("Company Does Not Have That Coupon"),
    INVALID_COUPON_VALUES("Coupon Does Not Meet Requirements"),
    DatesInvalid("Start Date And End Date Should Be at Least 3 Days Apart");


    final String clientValue;
    CompanyExceptionTypes(String clientValue){
        this.clientValue= clientValue;
    }


    @Override
    public String toString() {
        return this.clientValue;
    }
}
