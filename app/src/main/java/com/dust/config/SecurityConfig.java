package com.dust.config;

import com.dust.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.savedrequest.RequestCacheAwareFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().formLogin().disable().csrf().disable();
        http = http.exceptionHandling().authenticationEntryPoint(((request, response, authException) -> {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        })).and();
        http.authorizeRequests()
            .antMatchers("/api/auth/nonce").permitAll()
            .antMatchers("/api/auth/login").permitAll()
            .antMatchers("/api/inventory").permitAll()
            .anyRequest().authenticated();
        http.addFilterBefore(jwtFilter, RequestCacheAwareFilter.class);
    }
}
