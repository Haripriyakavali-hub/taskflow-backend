package com.taskflow.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter implements Filter {

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        System.out.println("🔥 JwtFilter CALLED");

        HttpServletRequest req = (HttpServletRequest) request;

        String header = req.getHeader("Authorization");
        System.out.println("HEADER: " + header);

        if (header != null && header.startsWith("Bearer ")) {

            String token = header.substring(7);
            System.out.println("TOKEN: " + token);

            try {
                String email = jwtUtil.extractEmail(token);
                System.out.println("AUTH SET FOR: " + email);

                if (!jwtUtil.isTokenExpired(token)) {

                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    email,
                                    null,
                                    Collections.emptyList()
                            );

                    SecurityContextHolder.getContext().setAuthentication(auth);

                } else {
                    System.out.println("❌ TOKEN EXPIRED");
                }

            } catch (Exception e) {
                System.out.println("JWT ERROR: " + e.getMessage());
            }

        } else {
            System.out.println("❌ NO VALID AUTH HEADER");
        }

        chain.doFilter(request, response);
    }
}