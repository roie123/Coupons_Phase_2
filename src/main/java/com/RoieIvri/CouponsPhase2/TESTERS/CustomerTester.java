package com.RoieIvri.CouponsPhase2.TESTERS;

import com.RoieIvri.CouponsPhase2.ADMIN.AdminService;
import com.RoieIvri.CouponsPhase2.CUSTOMER.Customer;
import com.RoieIvri.CouponsPhase2.CUSTOMER.CustomerService;
import com.RoieIvri.CouponsPhase2.CategoryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomerTester {


public static Long availableCouponId = 0L;
    private final CustomerService customerService;

private  final AdminService adminService;


    public void test() throws Exception {
        System.out.println();
        System.out.println();
        System.out.println("      CUSTOMER SERVICE TESTER");
        System.out.println();
        System.out.println();

        String email = UUID.randomUUID().toString().substring(0,5);
        String password = UUID.randomUUID().toString().substring(0,5);

        Customer customer = Customer.builder().firstName(UUID.randomUUID().toString().substring(0,5))
                .lastName(UUID.randomUUID().toString().substring(0,4))
                .email(email)
                .password(password)
                .build();

        Customer customerFromDB = adminService.addNewCustomer(customer);
        if (customerService.login(email,password)) System.out.println("LOGIN TEST          :                    ===> LOGGED IN");
            else System.out.println("LOGIN TEST          :                    ===> TRY AGAIN ");
        System.out.println(availableCouponId + " "+ customerFromDB.getId());
            customerService.purchaseCoupon(availableCouponId,customerFromDB.getId());
        System.out.println("PURCHASE COUPON TEST        : COUPON LIST SHOULD BE 1               ===>"+customerService.getCustomerCoupons(customerFromDB.getId()).size());
        System.out.println("GET CUSTOMER COUPONS TEST   : SHOULD BE ONE COUPON                  ===>"+customerService.getCustomerCoupons(customerFromDB.getId()));
        System.out.println("GET CUSTOMER COUPONS TEST   : BY CATEGORY : Category.StupidFace     ===>"+customerService.getCustomerCouponsByCategory(customerFromDB.getId(), CategoryType.StupidFace));
        System.out.println("GET CUSTOMER COUPONS TEST   : BY MAX PRICE : MAX = 60               ===>"+customerService.getCustomerCouponsUpToPrice(customerFromDB.getId(), 60L));
        customerService.getCustomerDetails(customerFromDB.getId());
    }

}
