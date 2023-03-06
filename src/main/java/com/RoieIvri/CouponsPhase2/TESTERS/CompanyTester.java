package com.RoieIvri.CouponsPhase2.TESTERS;

import com.RoieIvri.CouponsPhase2.COMPANY.Company;
import com.RoieIvri.CouponsPhase2.COMPANY.CompanyService;
import com.RoieIvri.CouponsPhase2.COUPON.Coupon;
import com.RoieIvri.CouponsPhase2.CategoryType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CompanyTester {


    @Autowired
    CompanyService companyService;

    public void test() throws Exception {
        Long companyId = 18L;
     companyService.login("2a019b1@shittyMail.com","477ca");
    Company company = companyService.getOneObject(companyId);
    Coupon coupon = new Coupon();
    coupon.setTitle("SHITTY COUPON" + UUID.randomUUID().toString().substring(0,5));
    companyService.addCoupon(coupon,companyId);
    coupon.setTitle("NEW TITLE");
//    companyService.updateCoupon(coupon,1L,companyId); //FOR TESTING
//        companyService.deleteCouponFromCompany(1L,companyId);
        companyService.getAllCompanyCoupons(companyId).stream().forEach(System.out::println);
        System.out.println(companyService.getCompanyCouponsByCategory(CategoryType.StupidFace, companyId));
        System.out.println("COUPONS UP TO PRICE ==> "+companyService.getCompanyCouponUpToPrice(3L,companyId));

    }
}
