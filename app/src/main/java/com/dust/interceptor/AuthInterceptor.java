package com.dust.interceptor;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dust.util.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String jwt = Arrays.stream(request.getCookies()).filter(cookie -> "jwt".equalsIgnoreCase(cookie.getName())).map(Cookie::getValue).findAny().orElse(null);
        if (jwt == null) {
            return false;
        }
        try {
            DecodedJWT decodedJWT = JwtUtils.verifyJwt(jwt);
            Base64.Decoder decoder = Base64.getUrlDecoder();
            String payloadStr = new String(decoder.decode(decodedJWT.getPayload()));
            ObjectMapper mapper = new ObjectMapper();
            Map payload = mapper.readValue(payloadStr, Map.class);
            request.setAttribute("userUuid", payload.get("userUuid"));
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }
}
