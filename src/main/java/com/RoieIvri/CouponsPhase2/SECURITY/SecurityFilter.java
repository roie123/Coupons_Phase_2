package com.RoieIvri.CouponsPhase2.SECURITY;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component@RequiredArgsConstructor
public class SecurityFilter  extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String headers = request.getHeader("Authorization");
        if (headers== null || !headers.startsWith("Bearer")){
            System.out.println("S");
            filterChain.doFilter(request,response);

        }else {

        }
    }
}
