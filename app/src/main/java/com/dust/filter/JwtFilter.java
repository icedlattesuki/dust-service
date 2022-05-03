package com.dust.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.dust.util.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = Arrays.stream(request.getCookies()).filter(cookie -> "jwt".equalsIgnoreCase(cookie.getName())).map(Cookie::getValue).findAny().orElse(null);
            DecodedJWT decodedJWT = JwtUtils.verifyJwt(jwt);
            Base64.Decoder decoder = Base64.getUrlDecoder();
            String payloadStr = new String(decoder.decode(decodedJWT.getPayload()));
            ObjectMapper mapper = new ObjectMapper();
            Map payload = mapper.readValue(payloadStr, Map.class);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(payload.get("userUuid"), null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            doFilter(request, response, filterChain);
        } catch (Exception e) {
            doFilter(request, response, filterChain);
        }
    }
}
