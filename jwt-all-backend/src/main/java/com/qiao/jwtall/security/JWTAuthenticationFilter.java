package com.qiao.jwtall.security;

import com.qiao.jwtall.exception.CustomException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Order(1)
public class JWTAuthenticationFilter implements Filter {

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String URI = req.getRequestURI();
        // No authentication needed
        if (URI.startsWith("/notice") || URI.startsWith("/auth/login") || URI.startsWith("/h2-console") || URI.startsWith("/favicon.ico")) {
            chain.doFilter(request, response);
            return;
        }

        String token = jwtTokenProvider.resolveToken(req);
        if (StringUtils.isBlank(token)) {
            throw new CustomException("Not Authenticated", HttpStatus.UNAUTHORIZED);
        }

        jwtTokenProvider.validateToken(token);
        chain.doFilter(request, response);
    }

}
