package com.RoieIvri.CouponsPhase2.SECURITY;

import com.RoieIvri.CouponsPhase2.COMPANY.Company;
import com.RoieIvri.CouponsPhase2.COMPANY.CompanyService;
import com.RoieIvri.CouponsPhase2.CUSTOMER.Customer;
import com.RoieIvri.CouponsPhase2.CUSTOMER.CustomerService;
import lombok.RequiredArgsConstructor;
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
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (username.equals("Admin@gmail.com")){
            return new User("Admin@gmail.com","Administhebest", List.of(new SimpleGrantedAuthority(Authorities.ROLE_ADMIN.toString())));
        }
        Company company = companyService.getByEmail(username);

        if (company!= null){
            return companyService.getByEmail(username);
        }


        Customer customer = customerService.getByEmail(username);
        System.out.println(customer);
        if (customer!=null){
            System.out.println("returninig customer");
            return customer;
        }

       return null;
    }
}
