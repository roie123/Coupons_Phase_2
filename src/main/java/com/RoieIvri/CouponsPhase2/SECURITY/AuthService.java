package com.RoieIvri.CouponsPhase2.SECURITY;

import com.RoieIvri.CouponsPhase2.COMPANY.CompanyService;
import com.RoieIvri.CouponsPhase2.CUSTOMER.CustomerService;
import com.RoieIvri.CouponsPhase2.LOGIN_MANAGER.ClientType;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.util.ToStringUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenConfig tokenConfig;

    private final CustomerService customerService;
    private final CompanyService companyService;
    public TokenResponseDTO validateLoginDetails(LoginRequestDTO loginRequestDTO) {
        boolean isCompatible = false;
        switch (loginRequestDTO.getClientType()){

            case Admin -> {
                isCompatible=true;
            }
            case Company -> {
                isCompatible = companyService.existByEmail(loginRequestDTO.getUserName());
            }
            case Customer -> {
                isCompatible = customerService.existsBuEmail(loginRequestDTO.getUserName());
            }
        }
        boolean isLoginValid = this.isLoginDetailsValid(loginRequestDTO);
        if (isLoginValid && isCompatible) {
            System.out.println("USER LOGIN VAILD ===>>>>    ");

            return new TokenResponseDTO(this.tokenConfig.generateToken(this.buildClaims(loginRequestDTO)));
        }

        return null;
    }


    private boolean isLoginDetailsValid(LoginRequestDTO loginRequestDTO) {
        System.out.println("isLoginDetailsValid FUNC" + loginRequestDTO);

        try {
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.getUserName(),
                            loginRequestDTO.getPassword(),
                            List.of(new SimpleGrantedAuthority(getAuthByClientType(loginRequestDTO.getClientType()).toString()))
                    )
            );

            System.out.println("isLoginDetailsValid FUNC END ");


        } catch (BadCredentialsException e) {
            System.out.println(e.toString());

            return false;
        }

        return true;

    }


    private Map<String, Object> buildClaims(LoginRequestDTO loginRequestDTO) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userName", loginRequestDTO.getUserName());
        claims.put("role", loginRequestDTO.getClientType());
        return claims;
    }
    private Authorities getAuthByClientType(ClientType clientType){
        switch (clientType){

            case Admin -> {
                return Authorities.ROLE_ADMIN;
            }
            case Company -> {
                return Authorities.ROLE_COMPANY;
            }
            case Customer -> {
                return Authorities.ROLE_CUSTOMER;
            }
        }
        return null;
    }

}
