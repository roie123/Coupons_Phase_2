package com.RoieIvri.CouponsPhase2.SECURITY;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    public TokenResponseDTO validateLoginDetails(LoginRequestDTO loginRequestDTO) {
        System.out.println(this.isLoginDetailsValid(loginRequestDTO));


        return null;
    }


    private boolean isLoginDetailsValid(LoginRequestDTO loginRequestDTO) {
        try {
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.getUserName(),
                            loginRequestDTO.getPassword()
                    )
            );


        } catch (BadCredentialsException e) {
            System.out.println(e.getMessage());
            ;
            return false;
        }

        return true;
    }
}
