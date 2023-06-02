package com.RoieIvri.CouponsPhase2.SECURITY;

import com.RoieIvri.CouponsPhase2.COMPANY.Company;
import com.RoieIvri.CouponsPhase2.COMPANY.CompanyService;
import com.RoieIvri.CouponsPhase2.CUSTOMER.Customer;
import com.RoieIvri.CouponsPhase2.CUSTOMER.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final CompanyService companyService;
    private final CustomerService customerService;
    @SneakyThrows
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername FUNC");
        if (username.equals("Admin@gmail.com")){
            return new User("Admin@gmail.com","Administhebest", List.of(new SimpleGrantedAuthority(Authorities.ROLE_ADMIN.toString())));
        }
        Company company = companyService.getByEmail(username);
        System.out.println(company);
        if (company!= null){
            System.out.println("HI");
            return company;
        }


        Customer customer = customerService.getByEmail(username);

        if (customer!=null){

            return customer;
        }
        throw new SecurityException(SecurityExceptionType.EmailNotFound.toString());


    }
}
