package com.aditya.todoApp.CorsConfig;

import com.aditya.todoApp.Model.AppUser;
import com.aditya.todoApp.Repo.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtFilter extends OncePerRequestFilter {

    private JwtUtillity jwtUtil;
    private UserRepository userRepo;

    public JwtFilter(JwtUtillity jwtUtil, UserRepository userRepo){
        this.jwtUtil = jwtUtil;
        this.userRepo = userRepo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if(header != null && header.startsWith("Bearer ")){
            String token = header.substring(7);

            if(jwtUtil.validateToken(token)){
                String userName = jwtUtil.getUsernameFromToken(token);
                AppUser user = userRepo.findByUserName(userName).orElse(null);

                if(user != null){
                    // Set proper authority so Spring Security doesn't block the request
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(userName, null, List.of(new SimpleGrantedAuthority("USER")));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
