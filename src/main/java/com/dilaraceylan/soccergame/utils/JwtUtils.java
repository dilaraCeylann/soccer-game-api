package com.dilaraceylan.soccergame.utils;

import java.util.Date;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.dilaraceylan.soccergame.entities.dto.UserDTO;

import io.jsonwebtoken.*;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * @author dilara.ceylan
 */
@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    private static String jwtSecret = "421FF9752DAA799BE494C6DB24D7BBFA2FFD90D498EA00AE81BB266E110DA2D2";

    @Value("${dilaraceylan.soccergame.jwtExpirationMs}")
    private int jwtExpirationMs;

    /**
     * 
     * @param authentication
     * @return
     */
    public String generateJwtToken(Authentication authentication) {

        UserDTO userPrincipal = (UserDTO) authentication.getPrincipal();

        return Jwts.builder().setSubject((userPrincipal.getUsername()))
                        .claim("userId", userPrincipal.getId()).setIssuedAt(new Date())
                        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                        .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    /**
     * 
     * @param token
     * @return
     */
    public static String getUserNameFromJwtToken(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
        if (Objects.nonNull(claims) && Objects.nonNull(claims.getBody())) {
            return claims.getBody().getSubject();
        }

        return null;
    }

    /**
     * 
     * @param token
     * @return
     */
    public static Long getUserIdFromToken(String token) {

        if (Objects.isNull(token)) {

        }

        Jws<Claims> claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
        if (Objects.nonNull(claims) && Objects.nonNull(claims.getBody())) {
            return claims.getBody().get("userId", Long.class);
        }

        return null;
    }

    /**
     * 
     * @param token
     * @return
     */
    public static Date decodeJwt(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getExpiresAt();
    }

    /**
     * 
     * @param request
     * @return
     */
    public static String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }

    /**
     * 
     * @return
     */
    public static String getTokenFromHeader() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                        .getRequestAttributes();
        if (Objects.isNull(servletRequestAttributes))
            return null;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return parseJwt(request);
    }

    /**
     * @param authToken
     * @return
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
