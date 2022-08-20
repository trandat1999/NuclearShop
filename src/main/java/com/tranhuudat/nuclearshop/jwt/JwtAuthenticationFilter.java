package com.tranhuudat.nuclearshop.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tranhuudat.nuclearshop.config.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserDetailsServiceImpl accountDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest arg0, HttpServletResponse arg1, FilterChain arg2)
            throws ServletException, IOException {
        String jwtFromrequest = getJwtFromRequest(arg0);

        if (StringUtils.hasText(jwtFromrequest) && jwtProvider.validateToken(jwtFromrequest)) {
            String username = jwtProvider.getUsernameFromToken(jwtFromrequest);
            UserDetails userDetails =   accountDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                    null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(arg0));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        arg2.doFilter(arg0, arg1);
    }

    private String getJwtFromRequest(HttpServletRequest arg0) {
        String token = arg0.getHeader("Authorization");
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }

}
