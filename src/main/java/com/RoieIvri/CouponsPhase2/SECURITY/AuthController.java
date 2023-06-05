package com.RoieIvri.CouponsPhase2.SECURITY;

import com.RoieIvri.CouponsPhase2.ADMIN.AdminService;
import com.RoieIvri.CouponsPhase2.COMPANY.Company;
import com.RoieIvri.CouponsPhase2.CUSTOMER.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
    private final AuthService authService;
    private final AdminService adminService;

    @PostMapping("/login")
    public TokenResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO){
        System.out.println("Login request reached ");
      return   authService.validateLoginDetails(loginRequestDTO);

    }


    @PostMapping("/registration/company")
    public TokenResponseDTO registration(@RequestBody Company company) throws Exception {
        return this.adminService.addCompany(company);
    }

    @PostMapping("/registration/customer")
    public Long registration(@RequestBody Customer customer) throws Exception {
        return this.adminService.addNewCustomer(customer);
    }






}
