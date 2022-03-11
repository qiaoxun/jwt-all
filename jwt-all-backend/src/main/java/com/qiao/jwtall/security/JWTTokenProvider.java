package com.qiao.jwtall.security;

import com.qiao.jwtall.dto.UserDTO;
import com.qiao.jwtall.exception.CustomException;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Date;

@Component
public class JWTTokenProvider {

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds = 3600000; // 1h

    Logger logger = LoggerFactory.getLogger(JWTTokenProvider.class);

    // https://stackoverflow.com/questions/34595605/how-to-manage-exceptions-thrown-in-filters-in-spring
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UserDTO userDTO) {
        Claims claims = Jwts.claims().setSubject(userDTO.getUsername());
        claims.put("user", userDTO);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        System.out.println("secretKey is :" + secretKey);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

//        public Authentication getAuthentication(String token) {
//            UserDetails userDetails = myUserDetails.loadUserByUsername(getUsername(token));
//            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
//        }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token, HttpServletRequest request, HttpServletResponse response) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            if (e instanceof ExpiredJwtException) {

            }
            logger.info("Validate token failed", e.getMessage());
            resolver.resolveException(request, response, null, new CustomException("Not Authenticated", HttpStatus.UNAUTHORIZED));
            return false;
        }
    }

}
