package com.dust.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.Map;

public class JwtUtils {

    public static final String key = "B84CCF964A87CADE628AF8393C8A85B3FB586FE8C7DCCE5724C5C51FD7";

    public static String createJwt(Map<String, Object> payload) throws JWTCreationException {
        Algorithm algorithm = Algorithm.HMAC512(key);
        return JWT.create().withIssuer("Dust").withIssuedAt(new Date()).withPayload(payload).sign(algorithm);
    }

    public static DecodedJWT verifyJwt(String jwt) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC512(key);
        JWTVerifier verifier = JWT.require(algorithm).withIssuer("Dust").build();
        return verifier.verify(jwt);
    }
}
