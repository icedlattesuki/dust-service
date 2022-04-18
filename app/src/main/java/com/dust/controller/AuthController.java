package com.dust.controller;

import com.dust.controller.request.LoginRequest;
import com.dust.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping(path = "/api/auth/nonce")
    public String getNonce(@RequestParam("publicAddress") String publicAddress) {
        return "Login Nonce = " + authService.getNonce(publicAddress);
    }

    @PostMapping(path = "/api/auth/login")
    public void login(@RequestBody LoginRequest request, HttpServletResponse response) {
        String jwt = authService.login(request.getPublicAddress(), request.getSignature());
        Cookie cookie = new Cookie("jwt", jwt);
        cookie.setPath("/");
        cookie.setMaxAge(Integer.MAX_VALUE);
        response.addCookie(cookie);
    }

    @GetMapping(path = "/api/test")
    public void test(@RequestAttribute("publicAddress") String publicAddress) {
        System.out.println(publicAddress);
    }
}
