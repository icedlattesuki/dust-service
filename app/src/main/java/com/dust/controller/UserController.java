package com.dust.controller;

import com.dust.controller.request.*;
import com.dust.controller.response.UserProfileResponse;
import com.dust.service.UserService;
import com.dust.service.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/api/user/profile")
    public UserProfileResponse getProfile(Authentication auth) {
        String userUuid = (String) auth.getPrincipal();
        User user = userService.getUser(userUuid);
        UserProfileResponse response = new UserProfileResponse();
        response.setPublicAddress(user.getPublicAddress());
        response.setUserUuid(user.getUserUuid());
        response.setName(user.getUsername());
        response.setAvatarUrl(user.getAvatarUrl());
        response.setSteamId(user.getSteamId());
        response.setApiKey(user.getApiKey());
        response.setTradeUrl(user.getTradeUrl());
        return response;
    }

    @PostMapping("/api/user/avatar")
    public String updateAvatar(Authentication auth, @RequestBody @Valid UpdateAvatarRequest request) {
        String userUuid = (String) auth.getPrincipal();
        return userService.updateAvatar(userUuid, request.getAvatarKey());
    }

    @PostMapping("/api/user/username")
    public void updateUsername(Authentication auth, @RequestBody @Valid UpdateUsernameRequest request) {
        String userUuid = (String) auth.getPrincipal();
        userService.updateUsername(userUuid, request.getUsername());
    }

    @PostMapping("/api/user/steamid")
    public String bindSteamId(Authentication auth, @RequestBody @Valid BindSteamIdRequest request) {
        String userUuid = (String) auth.getPrincipal();
        return userService.bindSteamId(userUuid, request.getSteamReturnUrl());
    }

    @PostMapping("/api/user/steam-api-key")
    public void updateSteamApiKey(Authentication auth, @RequestBody @Valid UpdateSteamApiKeyRequest request) {
        String userUuid = (String) auth.getPrincipal();
        userService.updateSteamApiKey(userUuid, request.getSteamApiKey());
    }

    @PostMapping("/api/user/steam-trade-url")
    public void updateSteamTradeUrl(Authentication auth, @RequestBody @Valid UpdateSteamTradeUrlRequest request) {
        String userUuid = (String) auth.getPrincipal();
        userService.updateSteamTradeUrl(userUuid, request.getSteamTradeUrl());
    }
}
