package com.RoieIvri.CouponsPhase2.SECURITY;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.catalina.util.ToStringUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenConfig tokenConfig;
    private final UserService userService;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader == null || !tokenHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);

            return;
        }
        final String token = tokenHeader.substring(7);
        System.out.println(token);
        String userName = this.tokenConfig.getUserNameFromToken(token);
        System.out.println(userName);

        if (userName != null) {
            boolean isTokenExpirationValid = this.tokenConfig.isExpirationToken(token);
            if (isTokenExpirationValid) {
                UserDetails userDetails = this.userService.loadUserByUsername(userName);
                System.out.println("Request User Details " + userDetails);

                if (userDetails== null){
                    throw new SecurityException(SecurityExceptionType.EmailNotFound.toString());
                }


                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken authentication
                            = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println(authentication.getAuthorities());
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
