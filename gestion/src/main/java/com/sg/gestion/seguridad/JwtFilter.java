package com.sg.gestion.seguridad;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService userS;

    @Autowired
    private JwtToken jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        String tk = null;
        String email = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")){
            tk = authHeader.substring(7);
            email = jwtUtil.extractUsername(tk);
        }

        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userS.loadUserByUsername(email);
            if(jwtUtil.validateToken(tk, userDetails)){
                //log.info("Usuario validado");
                UsernamePasswordAuthenticationToken authTK = new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities());
                authTK.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authTK);
            }
        }
        filterChain.doFilter(request,response);
    }

}
