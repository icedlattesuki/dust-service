package com.dust.controller;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.dust.controller.request.LoginRequest;
import com.dust.service.UserService;
import com.dust.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/api/user/nonce")
    public String getNonce(@RequestParam("publicAddress") String publicAddress) {
        if (publicAddress == null) {
            return null;
        }
        return "Login Nonce = " + userService.getNonce(publicAddress);
    }

    @PostMapping(path = "/api/user/login")
    public String login(@RequestBody LoginRequest request, HttpServletResponse response) {
        if (userService.login(request.getPublicAddress(), request.getSignature())) {
            Map<String, Object> payload = new HashMap<>();
            payload.put("publicAddress", request.getPublicAddress());
            try {
                Cookie cookie = new Cookie("jwt", JwtUtils.createJwt(payload));
                cookie.setPath("/");
                cookie.setMaxAge(Integer.MAX_VALUE);
                response.addCookie(cookie);
                return "success";
            } catch (JWTCreationException exception) {
                return "fail to create jwt";
            }
        } else {
            return "no auth";
        }
    }

    @GetMapping(path = "/api/test")
    public void test(@RequestAttribute("publicAddress") String publicAddress) {
        System.out.println(publicAddress);
    }
}
