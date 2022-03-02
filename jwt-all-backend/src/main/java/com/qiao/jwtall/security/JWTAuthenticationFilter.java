package com.qiao.jwtall.security;

import com.qiao.jwtall.exception.CustomException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    // https://stackoverflow.com/questions/34595605/how-to-manage-exceptions-thrown-in-filters-in-spring
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String URI = request.getRequestURI();
        // No authentication needed
//        if (URI.startsWith("/notice") || URI.startsWith("/auth/login") || URI.startsWith("/h2-console")
//                || URI.startsWith("/favicon.ico") || URI.startsWith("/index.html") || URI.equals("/")
//                || URI.equals("") || URI.startsWith("/js/") || URI.startsWith("/css/") || URI.startsWith("/login")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String token = jwtTokenProvider.resolveToken(request);
//        if (StringUtils.isBlank(token)) {
//            resolver.resolveException(request, response, null, new CustomException("Not Authenticated", HttpStatus.UNAUTHORIZED));
//            return;
//        }
//
//        boolean result = jwtTokenProvider.validateToken(token, request, response);
//        if (!result) return;
        filterChain.doFilter(request, response);
    }

}
