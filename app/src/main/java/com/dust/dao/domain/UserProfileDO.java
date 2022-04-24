package com.dust.dao.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileDO {

    private String userUuid;

    private String name;

    private String avatarKey;

    private String steamId;

    private String apiKey;

    private String tradeUrl;
}
