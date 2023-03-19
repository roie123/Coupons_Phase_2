package com.RoieIvri.CouponsPhase2.TESTERS;

import com.RoieIvri.CouponsPhase2.COMPANY.Company;
import com.RoieIvri.CouponsPhase2.COMPANY.CompanyService;
import com.RoieIvri.CouponsPhase2.COUPON.Coupon;
import com.RoieIvri.CouponsPhase2.COUPON.CouponService;
import com.RoieIvri.CouponsPhase2.CategoryType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CompanyTester {


    @Autowired
    CompanyService companyService;

    private final CouponService couponService;

    public void test() throws Exception {

        String randomName = UUID.randomUUID().toString().substring(0, 5);
        String randomEmail = randomName + "@gmail.com";
        String password = UUID.randomUUID().toString().substring(0, 4);
        Random random = new Random();

        Company company = Company.builder().name(randomName)
                .email(randomEmail)
                .password(password)
                .build();

        Company companyFromDb = companyService.addObject(company);


        System.out.println();
        System.out.println();
        System.out.println("      COMPANY TESTER");
        System.out.println();
        System.out.println();

        if (companyService.login(randomEmail, password))
            System.out.println("LOGIN STATUS                       : LOGIN SUCCESFULL                                                                                 ===> LOGGED IN  ");
        else
            System.out.println("LOGIN STATUS                       : LOGIN FAILED                                                                                     ===> TRY AGAIN  ");
        ;

        companyService.getCompanyDetails(companyFromDb.getId());

        Coupon coupon = Coupon.builder().title(UUID.randomUUID().toString().substring(0, 5))
                .price(random.nextInt(0, 50))
                .image("Stupid image")
                .startDate(LocalDate.now().minusDays(40))
                .endDate(LocalDate.now().plusDays(random.nextInt(3, 9)))
                .amount(4)
                .description("DESC " + UUID.randomUUID().toString().substring(0, 6))
                .category(CategoryType.StupidFace)
                .build();

        coupon = couponService.addObject(coupon);
        companyService.addCoupon(coupon, companyFromDb.getId());
        companyFromDb = companyService.getOneObjectWIthLists(companyFromDb.getId());
        System.out.println("ADDED COUPON TO COMPANY TEST       : COUPON VALID                                                                                     ===> ADD SUCCESFULLY ");
        System.out.println("COMPANY COUPONS TEST               : SHOULD BE  1                                                                                     ===> " + companyFromDb.getCouponList().size());
        System.out.println("GET ALL COUPONS TEST               :                                                                                                  ===> " + companyService.getAllCompanyCoupons(companyFromDb.getId()));
        System.out.println("GET ALL COUPONS BY CAT TEST        : Category.StupidFace                                                                              ===> " + companyService.getCompanyCouponsByCategory(CategoryType.StupidFace, companyFromDb.getId()));
        System.out.println("GET ALL COUPONS BY CAT TEST        : MAX = 42                                                                                         ===> " + companyService.getCompanyCouponUpToPrice(42L, companyFromDb.getId()));


        coupon.setTitle("NEW UPDATED TITLE ");
        companyService.updateCoupon(coupon, coupon.getId(), company.getId());
        companyFromDb = companyService.getOneObjectWIthLists(companyFromDb.getId());
        System.out.println("UPDATED COUPON TEST                : TITLE SHOULD BE 'NEW UPDATED TITLE'                                                              ===> " + companyFromDb.getCouponList().get(0).getTitle());
        companyService.deleteCouponFromCompany(coupon.getId(), company.getId());
        companyFromDb = companyService.getOneObjectWIthLists(companyFromDb.getId());
        System.out.println("COMPANY COUPONS DELETE  TEST       : LIST SIZE SHOULD BE 0                                                                            ===> " + " LIST SIZE  " + companyFromDb.getCouponList().size());






        Coupon coupon1 = Coupon.builder().title(UUID.randomUUID().toString().substring(0, 5))
                .price(random.nextInt(0, 50))
                .image("Stupid image")
                .startDate(LocalDate.now().minusDays(40))
                .endDate(LocalDate.now().plusDays(random.nextInt(3, 9)))
                .amount(4)
                .description("DESC " + UUID.randomUUID().toString().substring(0, 6))
                .category(CategoryType.StupidFace)
                .build();

        Coupon coupon1FromDB = couponService.addObject(coupon1);
        companyService.addCoupon(coupon, companyFromDb.getId());
        CustomerTester.availableCouponId=coupon1FromDB.getId();
    }
}
