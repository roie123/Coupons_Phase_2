package com.RoieIvri.CouponsPhase2.SECURITY;

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


    public TokenResponseDTO validateLoginDetails(LoginRequestDTO loginRequestDTO) {
        boolean isLoginValid = this.isLoginDetailsValid(loginRequestDTO);
        if (isLoginValid) {
            System.out.println("FFSFSFSF");
            return new TokenResponseDTO(this.tokenConfig.generateToken(this.buildClaims(loginRequestDTO)));
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



        } catch (Exception e) {
            System.out.println(e);

        }
        System.out.println("Hellp0");
        return true;

    }


    private Map<String, Object> buildClaims(LoginRequestDTO loginRequestDTO) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userName", loginRequestDTO.getUserName());
        claims.put("role", loginRequestDTO.getClientType());
        return claims;
    }




}
