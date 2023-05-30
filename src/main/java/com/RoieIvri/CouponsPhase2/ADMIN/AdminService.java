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
import com.RoieIvri.CouponsPhase2.SECURITY.LoginRequestDTO;
import com.RoieIvri.CouponsPhase2.SECURITY.TokenConfig;
import com.RoieIvri.CouponsPhase2.SECURITY.TokenResponseDTO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final CompanyService companyService;

    private final CouponService couponService;

    private final CustomerService customerService;

    private final TokenConfig tokenConfig;

    public boolean isLoginValid(String email,String password){
        if (email.equals("admin@admin.com") && password.equals("admin")){
            return true;
        }
        return false;

    }


    public TokenResponseDTO addCompany(Company company ) throws Exception {
    if (companyService.isCompanyValidToBeAdded(company)){
         Company company1=  companyService.addObject(company);
        String token = this.tokenConfig.generateToken(this.buildClaimsForCompany(company1));
        return new TokenResponseDTO(token);
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
    public TokenResponseDTO addNewCustomer(Customer customer) throws Exception {
        Customer customer1 = customerService.addObject(customer);

        String token = this.tokenConfig.generateToken(this.buildClaimsForCustomer(customer1));
        return new TokenResponseDTO(token);

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


    private Map<String, Object> buildClaimsForCompany(Company company) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userName", company.getUsername());
        claims.put("role", company.getAuthorities());
        return claims;
    }

    private Map<String, Object> buildClaimsForCustomer(Customer customer) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userName", customer.getUsername());
        claims.put("role", customer.getAuthorities());
        return claims;
    }

}
