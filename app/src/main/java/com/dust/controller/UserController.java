package com.dust.controller;

import com.dust.controller.response.UserProfileResponse;
import com.dust.service.SteamService;
import com.dust.service.UserService;
import com.dust.service.entity.SteamInfo;
import com.dust.service.entity.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userProfileService;

    @Autowired
    private SteamService steamService;

    @GetMapping("/api/user/profile")
    public UserProfileResponse getProfile(@RequestAttribute("userUuid") String userUuid) {
        UserProfile userProfile = userProfileService.getProfile(userUuid);
        SteamInfo steamInfo = steamService.getSteamInfo(userUuid);
        UserProfileResponse response = new UserProfileResponse();
        response.setPublicAddress(userProfile.getPublicAddress());
        response.setName(userProfile.getName());
        response.setAvatarUrl(userProfile.getAvatarUrl());
        if (steamInfo != null) {
            response.setSteamId(steamInfo.getSteamId());
            response.setApiKey(steamInfo.getApiKey());
            response.setTradeUrl(steamInfo.getTradeUrl());
        }
        return response;
    }
}
