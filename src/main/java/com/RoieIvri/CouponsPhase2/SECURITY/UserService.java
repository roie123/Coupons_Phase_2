package com.RoieIvri.CouponsPhase2.SECURITY;

import com.RoieIvri.CouponsPhase2.COMPANY.Company;
import com.RoieIvri.CouponsPhase2.COMPANY.CompanyService;
import com.RoieIvri.CouponsPhase2.CUSTOMER.Customer;
import com.RoieIvri.CouponsPhase2.CUSTOMER.CustomerService;
import com.RoieIvri.CouponsPhase2.LOGIN_MANAGER.ClientType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.logging.Log;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final CompanyService companyService;
    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;
    @SneakyThrows
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals("admin")){
            return new User("admin", this.passwordEncoder.encode("admin12345"), List.of(new SimpleGrantedAuthority(Authorities.ROLE_ADMIN.toString())));
        }
        Company company = companyService.getByEmail(username);
        System.out.println("COMPANY FROM LOAD BY USER NAME ==>>>> "+   company);
        if (company!= null){
            return new User(company.getUsername(),company.getPassword(),List.of(new SimpleGrantedAuthority(Authorities.ROLE_COMPANY.toString())));


        }


        Customer customer = customerService.getByEmail(username);
        System.out.println("CUSTOMER FROM LOAD BY USER NAME ==>>>> "+   customer);

        if (customer!=null){

            return new User(customer.getUsername(),customer.getPassword(),List.of(new SimpleGrantedAuthority(Authorities.ROLE_CUSTOMER.toString())));
        }
        throw new SecurityException(SecurityExceptionType.EmailNotFound.toString());


    }




}
