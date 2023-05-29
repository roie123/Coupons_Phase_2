package com.RoieIvri.CouponsPhase2.SECURITY;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenConfig tokenConfig;

    public TokenResponseDTO validateLoginDetails(LoginRequestDTO loginRequestDTO) {
        boolean isLoginValid = this.isLoginDetailsValid(loginRequestDTO);
        if (isLoginValid) {
            return new TokenResponseDTO(this.tokenConfig.generateToken(this.buildClaims(loginRequestDTO.getUserName())));
        }

        return null;
    }


    private boolean isLoginDetailsValid(LoginRequestDTO loginRequestDTO) {
        System.out.println(loginRequestDTO);
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


    private Map<String, Object> buildClaims(String userName) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userName", userName);
        claims.put("firstName", "Idan");
        claims.put("lastName", "Ayash");
        return claims;
    }
}
