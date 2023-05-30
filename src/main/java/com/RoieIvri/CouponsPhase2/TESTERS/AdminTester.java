//package com.RoieIvri.CouponsPhase2.TESTERS;
//
//
//import ch.qos.logback.classic.Logger;
//import com.RoieIvri.CouponsPhase2.ADMIN.AdminException;
//import com.RoieIvri.CouponsPhase2.ADMIN.AdminService;
//import com.RoieIvri.CouponsPhase2.COMPANY.ComapnyException;
//import com.RoieIvri.CouponsPhase2.COMPANY.Company;
//import com.RoieIvri.CouponsPhase2.COMPANY.CompanyDTO;
//import com.RoieIvri.CouponsPhase2.COMPANY.CompanyService;
//import com.RoieIvri.CouponsPhase2.COUPON.Coupon;
//import com.RoieIvri.CouponsPhase2.CUSTOMER.Customer;
//import com.RoieIvri.CouponsPhase2.CUSTOMER.CustomerDTO;
//import com.RoieIvri.CouponsPhase2.CategoryType;
//import com.RoieIvri.CouponsPhase2.LOGIN_MANAGER.ClientType;
//import com.RoieIvri.CouponsPhase2.LOGIN_MANAGER.LoginManager;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ansi.AnsiColor;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//import java.util.UUID;
//
//@Component
//@RequiredArgsConstructor
//public class AdminTester {
//
//
//    private final AdminService adminService;
//    private final CompanyService companyService;
//
//    private final LoginManager loginManager;
//
//
//    @Transactional
//    public void test() throws Exception {
//        Random random = new Random();
//        boolean loggedIn = false;
//        System.out.println();
//        System.out.println();
//        System.out.println("      ADMIN SERVICE TESTER ");
//        System.out.println();
//        System.out.println();
//        //LOGIN TEST
//        loggedIn = loginManager.login("admin@admin.com", "admin", ClientType.Admin);
//        if (loggedIn) {
//            Company company = Company.builder()
//                    .name(UUID.randomUUID().toString().substring(0, 4))
//                    .email(UUID.randomUUID().toString().substring(0, 4) + "@gmail.com")
//                    .password("Shit pass")
//                    .isActive(true)
//                    .build();
//
//            Company companyFromDb = adminService.addCompany(company);
//            companyFromDb = adminService.getSingleCompany(companyFromDb.getId()); // used the get single for the transactional attribute
//            System.out.println("THE CREATED COMPANY                :                                                                                                  ===> " + companyFromDb);
//
//
//            Coupon coupon = Coupon.builder()
//                    .amount(4)
//                    .category(CategoryType.StupidFace)
//                    .description("Desc " + UUID.randomUUID().toString().substring(0, 4))
//                    .price(random.nextDouble(4, 28))
//                    .startDate(LocalDate.now().minusDays(5))
//                    .endDate(LocalDate.now().plusDays(random.nextInt(0, 4)))
//                    .isActive(true)
//                    .title(UUID.randomUUID().toString().substring(0, 6))
//                    .image("Stupid Image")
//                    .build();
//            coupon.setCompany(companyFromDb);
//            companyFromDb.setName("STARLORD");
//            adminService.updateCompany(companyFromDb, companyFromDb.getId()); //ADDING A COMPANY
//            companyFromDb = adminService.getSingleCompany(companyFromDb.getId()); // used the get single for the transactional attribute
//            System.out.println("UPDATED COMPANY(GET SINGLE)        : UPDATED NAME SHOULD BE STARLORD                                                                  ===> " + companyFromDb); //Getting the real updated company with the coupon list
//            System.out.println("COUPON LIST                        : SHOULD BE NULL BECAUSE YOU CANT CREATE A COUPON FROM CREATING OR UPDATING A COMPANY              ===> " + companyFromDb.getCouponList());
//            adminService.deleteCompany(companyFromDb.getId());
//            try {
//                companyFromDb = adminService.getSingleCompany(companyFromDb.getId()); // used the get single for the transactional attribute
//
//            } catch (ComapnyException comapnyException) {
//                System.out.println("SEARCHING FOR COMPANY BY ID        : COMPANY NOT FOUND                                                                                ===> DELETE SUCCESSFUL");
//
//
//                List<CompanyDTO> companies = adminService.getAllCompanies();
//                System.out.println("GET ALL COMPANIES                  : CURRENT COMPANIES COUNT DOES NOT SUPPOSE TO CHANGE IN TEST                                       ===> " + companies.size() + " OVERALL");
//
//
//                System.out.println();
//                System.out.println("  CUSTOMER FEATURES IN ADMIN SERVICE");
//                System.out.println();
//                Customer customer = Customer.builder().isActive(true)
//                        .firstName("First " + UUID.randomUUID().toString().substring(0, 3))
//                        .lastName("Last " + UUID.randomUUID().toString().substring(0, 3))
//                        .password("SHIT PASS")
//                        .email(UUID.randomUUID().toString().substring(0, 3) + "@gmail.com")
//                        .build();
//
//                //Customer List Testing
//
//                customer = adminService.addNewCustomer(customer);
//                customer = adminService.getSingleCustomerById(customer.getId());
//                System.out.println("NEWLY CREATED CUSTOMER(GET SINGLE) : SAVED TO DATABASE                                                                                ===> " + customer);
//                adminService.deleteCustomer(customer.getId());
//                try {
//                    customer = adminService.getSingleCustomerById(customer.getId());
//                } catch (Exception exception) {
//                    System.out.println("SEARCHING FOR CUSTOMER BY ID       : CUSTOMER NOT FOUND                                                                               ===> DELETE SUCCESSFUL");
//
//                }
//                List<CustomerDTO> customers = adminService.getAllCustomers();
//                System.out.println("GET ALL CUSTOMERS                  : CURRENT CUSTOMERS COUNT SHOULD NOT CHANGE IN TEST                                                ===> " + customers.size() + " OVERALL");
//
//
//            }
//
//
//            //FINISH LOGIN TEST
//
//        }
//    }
//}
