package com.dilaraceylan.soccergame.config;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.dilaraceylan.soccergame.business.concrete.UserService;
import com.dilaraceylan.soccergame.security.jwt.AuthEntryPointJwt;
import com.dilaraceylan.soccergame.security.jwt.AuthTokenFilter;
/**
 * @author dilara.ceylan
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    private static final String[] AUTH_WHITELIST = {
                                                    // -- swagger ui
                                                    "/v2/api-docs", "/v3/api-docs",
                                                    "/swagger-resources/**", "/swagger-ui",
                                                    "/swagger-ui/**",};

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService)
                        .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().exceptionHandling()
                        .authenticationEntryPoint(unauthorizedHandler).and().sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                        .authorizeRequests()
                        .antMatchers("/api/transferList/**", "/api/team/**", "/api/player/**",
                                     "/api/authentication/**")
                        .permitAll().and().authorizeRequests().antMatchers(AUTH_WHITELIST)
                        .permitAll().anyRequest().authenticated();
        http.addFilterBefore(authenticationJwtTokenFilter(),
                             UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(new ArrayList<>(Arrays.asList("*")));
        configuration.setAllowedMethods(new ArrayList<>(
                        Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH")));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(new ArrayList<>(
                        Arrays.asList("Authorization", "Cache-Control", "Content-Type",
                                      "Content-Disposition", "Access-Control-Allow-Headers",
                                      "Access-Control-Expose-Headers", "Content-Language",
                                      "Expires", "Last-Modified", "Pragma", "www-authenticate")));
        configuration.setExposedHeaders(Arrays
                        .asList("Authorization", "Cache-Control", "Content-Type",
                                "Content-Disposition", "Access-Control-Allow-Headers",
                                "Access-Control-Expose-Headers", "Content-Language", "Expires",
                                "Last-Modified", "Pragma", "www-authenticate"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
