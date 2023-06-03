package com.RoieIvri.CouponsPhase2.ADMIN;

import com.RoieIvri.CouponsPhase2.COMPANY.Company;
import com.RoieIvri.CouponsPhase2.COMPANY.CompanyDTO;
import com.RoieIvri.CouponsPhase2.COMPANY.CompanyService;
import com.RoieIvri.CouponsPhase2.CUSTOMER.Customer;
import com.RoieIvri.CouponsPhase2.CUSTOMER.CustomerDTO;
import com.RoieIvri.CouponsPhase2.CUSTOMER.CustomerService;
import com.RoieIvri.CouponsPhase2.GenericTools.ClientController;
import com.RoieIvri.CouponsPhase2.SECURITY.Authorities;
import com.RoieIvri.CouponsPhase2.SECURITY.TokenResponseDTO;
import lombok.RequiredArgsConstructor;
import org.hibernate.loader.LoaderLogging;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@Secured({"ROLE_ADMIN"})
@CrossOrigin
public class AdminController extends ClientController {

    private  final  AdminService adminService;
    private final CustomerService customerService;

    @Override
    public boolean login(String email, String password) throws Exception {
        return adminService.isLoginValid(email, password);
    }


    @PostMapping()
    public TokenResponseDTO addCompany(@RequestBody Company company ) throws Exception {
        System.out.println(company);
        return adminService.addCompany(company);


    }

    @PutMapping("/{companyId}")
    public void updateCompany(@RequestBody Company company, @PathVariable Long companyId ) throws Exception {
        adminService.updateCompany(company,companyId);
    }

    @DeleteMapping("/deleteCompany/{companyId}")
    public void deleteCompany(@PathVariable Long companyId ) throws Exception {
        adminService.deleteCompany(companyId);

    }

    @GetMapping("/companies/{page}/{size}")
    public Page<Company> getAllCompanies(@PathVariable int page , @PathVariable int size) throws Exception {
        return adminService.getAllCompanies(page , size);
    }

    @GetMapping("/getCompanyById/{companyId}")
    public Company getSingle(@PathVariable Long companyId) throws Exception {
        return adminService.getSingleCompany(companyId);
    }




    @PostMapping("/customer")
    public TokenResponseDTO addNewCustomer(@RequestBody Customer customer) throws Exception {
        return adminService.addNewCustomer(customer);
    }

    @PutMapping("/customer/{customerId}")
    public void updateCustomer(@RequestBody Customer customer , @PathVariable Long customerId) throws Exception {
        customerService.updateObject(customer,customerId);
    }

    @DeleteMapping("/customer/{customerId}")
    public void deleteCustomer(@PathVariable Long customerId) throws Exception {
        customerService.deleteObject(customerId);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAll() throws Exception {
        return customerService.getAllObjects();
    }

    @GetMapping("/customer/{customerId}")
    public Customer getSingleCustomer(@PathVariable Long customerId) throws Exception {
        return customerService.getOneObject(customerId);
    }


}
