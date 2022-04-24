package com.dust.controller;

import com.dust.controller.request.*;
import com.dust.controller.response.UserProfileResponse;
import com.dust.service.UserService;
import com.dust.service.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/api/user/profile")
    public UserProfileResponse getProfile(@RequestAttribute("userUuid") String userUuid) {
        User user = userService.getUser(userUuid);
        UserProfileResponse response = new UserProfileResponse();
        response.setPublicAddress(user.getPublicAddress());
        response.setName(user.getUsername());
        response.setAvatarUrl(user.getAvatarUrl());
        response.setSteamId(user.getSteamId());
        response.setApiKey(user.getApiKey());
        response.setTradeUrl(user.getTradeUrl());
        return response;
    }

    @PostMapping("/api/user/avatar")
    public String updateAvatar(@RequestAttribute("userUuid") String userUuid, @RequestBody UpdateAvatarRequest request) {
        return userService.updateAvatar(userUuid, request.getAvatarKey());
    }

    @PostMapping("/api/user/username")
    public void updateUsername(@RequestAttribute("userUuid") String userUuid, @RequestBody UpdateUsernameRequest request) {
        userService.updateUsername(userUuid, request.getUsername());
    }

    @PostMapping("/api/user/steamid")
    public String bindSteamId(@RequestAttribute("userUuid") String userUuid, @RequestBody BindSteamIdRequest request) {
        return userService.bindSteamId(userUuid, request.getSteamReturnUrl());
    }

    @PostMapping("/api/user/steam-api-key")
    public void updateSteamApiKey(@RequestAttribute("userUuid") String userUuid, @RequestBody UpdateSteamApiKeyRequest request) {
        userService.updateSteamApiKey(userUuid, request.getSteamApiKey());
    }

    @PostMapping("/api/user/steam-trade-url")
    public void updateSteamTradeUrl(@RequestAttribute("userUuid") String userUuid, @RequestBody UpdateSteamTradeUrlRequest request) {
        userService.updateSteamTradeUrl(userUuid, request.getSteamTradeUrl());
    }
}
