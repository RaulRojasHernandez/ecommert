package com.tiid.tienda.filter;


import com.tiid.tienda.entities.User;
import com.tiid.tienda.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilterAuth extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
         final String token = getTokeFromFilter(request);
         final String email;

         if (token == null){
             filterChain.doFilter(request, response);
             return;
         }

         email=jwtService.getUserFromToken(token);

         if(email != null && jwtService.validateToken(token)){
             UserDetails userDetails = userDetailsService.loadUserByUsername(email);

             User user = (User) userDetails;

             if(jwtService.validateToken(token)){
                 UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                 authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                 SecurityContextHolder.getContext().setAuthentication(authenticationToken);



             }
             System.out.println("Valido");
         }

         filterChain.doFilter(request, response);
    }

    private String getTokeFromFilter(HttpServletRequest request){
        final String requestHeader = request.getHeader(HttpHeaders.AUTHORIZATION );

        if (StringUtils.hasText(requestHeader) && requestHeader.startsWith("Bearer ")){
            return requestHeader.substring(7);
        }
        return null;
    }
}
