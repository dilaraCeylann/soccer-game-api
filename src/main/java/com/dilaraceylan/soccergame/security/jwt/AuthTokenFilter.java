package com.dilaraceylan.soccergame.security.jwt;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dilaraceylan.soccergame.business.concrete.UserService;
import com.dilaraceylan.soccergame.utils.JwtUtils;

import io.jsonwebtoken.MalformedJwtException;
import net.bytebuddy.implementation.bytecode.Throw;

/**
 * @author dilara.ceylan
 */
public class AuthTokenFilter extends OncePerRequestFilter {
	
  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private UserService userService;

  private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String jwt = JwtUtils.parseJwt(request);
      if (Objects.nonNull(jwt) && jwtUtils.validateJwtToken(jwt)) {
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        if(Objects.isNull(username)) {
            throw new MalformedJwtException("Token is incorrect");    
        }
        UserDetails userDetails = userService.loadUserByUsername(username);
        
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      logger.error("Cannot set user authentication: {}", e);
    }

    filterChain.doFilter(request, response);
  }
}
