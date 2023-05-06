package com.RoieIvri.CouponsPhase2.ADMIN;

import com.RoieIvri.CouponsPhase2.COMPANY.Company;
import com.RoieIvri.CouponsPhase2.COMPANY.CompanyDTO;
import com.RoieIvri.CouponsPhase2.COMPANY.CompanyRepo;
import com.RoieIvri.CouponsPhase2.COMPANY.CompanyService;
import com.RoieIvri.CouponsPhase2.COUPON.Coupon;
import com.RoieIvri.CouponsPhase2.COUPON.CouponService;
import com.RoieIvri.CouponsPhase2.CUSTOMER.Customer;
import com.RoieIvri.CouponsPhase2.CUSTOMER.CustomerDTO;
import com.RoieIvri.CouponsPhase2.CUSTOMER.CustomerService;
import com.RoieIvri.CouponsPhase2.CategoryType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AdminService {
    @Autowired
    private final CompanyService companyService;

    @Autowired
    private final CouponService couponService;

    @Autowired
    private final CustomerService customerService;

    public boolean isLoginValid(String email,String password){
        if (email.equals("admin@admin.com") && password.equals("admin")){
            return true;
        }
        return false;

    }


    public Company addCompany(Company company ) throws Exception {
    if (companyService.isCompanyValidToBeAdded(company)){
      return  companyService.addObject(company);
    }
    else throw new AdminException("COMPANY COULD NOT BE ADDED :: NOT VALID VALUES");
    }
    public void updateCompany(Company company,Long companyId) throws Exception {
         companyService.updateObject(company,companyId);
    }

    public void deleteCompany(Long id) throws Exception {
        companyService.deleteObject(id);
    }


    public List<CompanyDTO> getAllCompanies() throws Exception {
        return companyService.getAllCompaniesSecured();
    }

    public Company getSingleCompany(Long id) throws Exception {
        return companyService.getOneObject(id);
    }
    public Customer addNewCustomer(Customer customer) throws Exception {
      return   customerService.addObject(customer);
    }
    public void deleteCustomer(Long objectId) throws Exception {
        customerService.deleteObject(objectId);
    }

    public List<CustomerDTO> getAllCustomers() throws Exception {
        return customerService.getAllObjects();
    }

    public Customer getSingleCustomerById(Long objectId) throws Exception {
        return customerService.getOneObject(objectId);
    }




}
