package com.RoieIvri.CouponsPhase2.TESTERS;


import com.RoieIvri.CouponsPhase2.ADMIN.AdminService;
import com.RoieIvri.CouponsPhase2.COMPANY.ComapnyException;
import com.RoieIvri.CouponsPhase2.COMPANY.Company;
import com.RoieIvri.CouponsPhase2.COMPANY.CompanyService;
import com.RoieIvri.CouponsPhase2.COUPON.Coupon;
import com.RoieIvri.CouponsPhase2.CUSTOMER.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AdminTester {


    private  final AdminService adminService;
    private  final CompanyService companyService;


    @Transactional
public  void test() throws Exception {
    Company company = new Company();
    company.setName(UUID.randomUUID().toString().substring(0,7));
    company.setPassword(UUID.randomUUID().toString().substring(0,5));
    company.setEmail(company.getName()+"@shittyMail.com");
    List<Coupon> couponList = new ArrayList<>();

    for (int i = 0; i < 4; i++) {
        Coupon coupon = new Coupon("Title Of Coupon " + i , "Shit desc" , LocalDate.now().plusDays(30),LocalDate.now().plusDays(3),3,3L,"");
        coupon.setCompany(company);
        couponList.add(coupon);
    }

    company.setCouponList(couponList);
    adminService.addCompany(company);
//    adminService.addCompany(company); //Checking to see if you can  add a company with the same values
    Company companyFromDb = companyService.getOneObject(6L);
    System.out.println("COMPANY FROM DB ::: => "+ companyFromDb);
        System.out.print("TESTING DELETING COMPANY WITH ITS COUPONS ::: =>  ");
//        companyService.deleteObject(companyFromDb.getId());

//        companyService.getOneObject(companyFromDb.getId()); //testing for deleting company

        System.out.println("GET ALL CURRENT COMPANIES => ");
        System.out.println(adminService.getAllCompanies());
        System.out.println(adminService.getSingleCompany(6L));

        Customer customer = new Customer();
        customer.setFirstName(UUID.randomUUID().toString().substring(0,5));
        customer.setLastName("SHIT");
        customer.setEmail(customer.getFirstName()+"@shitface.com");
//        customer.setCoupons(companyFromDb.getCouponList());
        adminService.addNewCustomer(customer);
//        adminService.de
//adminService.deleteCustomer(2L); // testing deleting
    adminService.getAllCustomers().forEach(customer1 -> System.out.println("GET ALL CUSTOMERS ==> "+ customer1));
        System.out.println("GET SINGLE CUSTOMER BY ID ==>  " +adminService.getSingleCustomerById(1L));

    }


}
