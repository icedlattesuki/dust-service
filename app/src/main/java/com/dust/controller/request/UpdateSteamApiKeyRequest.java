package com.dust.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class UpdateSteamApiKeyRequest {

    @Pattern(regexp = "^https://steamcommunity.com/tradeoffer/new/\\?partner=[0-9]{1,12}&token=[A-Za-z0-9]{8}$", message = "Steam Api Key不符合规则")
    private String steamApiKey;
}
