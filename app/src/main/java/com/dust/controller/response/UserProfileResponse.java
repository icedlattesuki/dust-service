package com.dust.controller.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileResponse {

    private String publicAddress;

    private String name;

    private String avatarUrl;

    private String steamId;

    private String apiKey;

    private String tradeUrl;
}
