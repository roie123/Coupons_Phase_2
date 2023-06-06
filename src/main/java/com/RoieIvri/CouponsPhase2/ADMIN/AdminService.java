package com.RoieIvri.CouponsPhase2.ADMIN;

import com.RoieIvri.CouponsPhase2.COMPANY.*;
import com.RoieIvri.CouponsPhase2.COUPON.CouponService;
import com.RoieIvri.CouponsPhase2.CUSTOMER.Customer;
import com.RoieIvri.CouponsPhase2.CUSTOMER.CustomerDTO;
import com.RoieIvri.CouponsPhase2.CUSTOMER.CustomerException;
import com.RoieIvri.CouponsPhase2.CUSTOMER.CustomerService;
import com.RoieIvri.CouponsPhase2.SECURITY.TokenConfig;
import com.RoieIvri.CouponsPhase2.SECURITY.TokenResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final CompanyService companyService;

    private final CouponService couponService;

    private final CustomerService customerService;

    private final TokenConfig tokenConfig;
    private final PasswordEncoder passwordEncoder;

    public boolean isLoginValid(String email,String password){
        if (email.equals("admin@admin.com") && password.equals("admin")){
            return true;
        }
        return false;

    }


    public TokenResponseDTO addCompany(Company company ) throws Exception {
    if (companyService.isCompanyValidToBeAdded(company)){
            company.setPassword(passwordEncoder.encode(company.getPassword()));

         Company company1=  companyService.addObject(company);
        String token = this.tokenConfig.generateToken(this.buildClaimsForCompany(company1));
        return new TokenResponseDTO(token);
    }
    else throw new AdminException("Company Could Not Be Added  , Not Valid Values");
    }
    public void updateCompany(Company company,Long companyId) throws Exception {
         companyService.updateObject(company,companyId);
    }

    public void deleteCompany(Long id) throws Exception {
        companyService.deleteObject(id);
    }


    public Page<Company> getAllCompanies(int page, int size) throws Exception {


        return companyService.getAll(page,size);
    }

    public Company getSingleCompany(Long id) throws Exception {
        Company company=  companyService.getOneObject(id);
        if (company==null){
            throw new ComapnyException(CompanyExceptionTypes.COMPANY_NOT_FOUND_BY_ID);
        }
        return company;
    }
    public Long addNewCustomer(Customer customer) throws Exception {
        Customer customer1 = customerService.addObject(customer);

//        String token = this.tokenConfig.generateToken(this.buildClaimsForCustomer(customer1));
        return customer1.getId();

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
