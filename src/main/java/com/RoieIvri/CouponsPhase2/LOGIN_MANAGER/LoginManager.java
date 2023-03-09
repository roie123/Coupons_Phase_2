package com.RoieIvri.CouponsPhase2.LOGIN_MANAGER;

import com.RoieIvri.CouponsPhase2.ADMIN.AdminService;
import com.RoieIvri.CouponsPhase2.COMPANY.CompanyService;
import com.RoieIvri.CouponsPhase2.COUPON.CouponService;
import com.RoieIvri.CouponsPhase2.CUSTOMER.Customer;
import com.RoieIvri.CouponsPhase2.CUSTOMER.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginManager {


    private final AdminService adminService;
    private final CustomerService customerService;
    private final CompanyService companyService;


    public boolean login(String email, String password, ClientType clientType) throws Exception {
        switch (clientType){
            case Admin -> {
                return adminService.isLoginValid(email,password);
            }
            case Company -> {
                return companyService.isLoginValid(email,password);
            }
            case Customer -> {
                return customerService.login(email,password);
            }
        }
    throw new LoginExceptions(LoginExceptionTypes.invalid_Login_Values);
    }

}
