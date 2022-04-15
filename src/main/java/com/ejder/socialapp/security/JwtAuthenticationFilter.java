package com.ejder.socialapp.security;

import com.ejder.socialapp.business.abstracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt=extractJwtFromRequest(request);
            if (jwt!=null &&jwtTokenProvider.validateToken(jwt)){
                var userName= jwtTokenProvider.getUsernameFromJwt(jwt);
                var user= userDetailsService.loadUserByUsername(userName);
                if(user!=null){
                    UsernamePasswordAuthenticationToken auth= new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }catch (Exception e){
            return ;
        }
        filterChain.doFilter(request,response);
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String header= request.getHeader("Authorization");
        if(StringUtils.hasText(header)&&header.startsWith("Bearer ")){
            return header.substring("Bearer".length()+1);
        }return null;
    }
}
