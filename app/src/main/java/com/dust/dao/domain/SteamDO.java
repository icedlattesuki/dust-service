package com.dust.dao.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SteamDO {

    private String userUuid;

    private String steamId;

    private String apiKey;

    private String tradeUrl;
}
