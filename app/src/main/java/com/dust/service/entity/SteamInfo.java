package com.dust.service.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SteamInfo {

    private String userUuid;

    private String steamId;

    private String apiKey;

    private String tradeUrl;
}
