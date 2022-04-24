package com.dust.service.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User extends UserBasic{

    private String steamId;

    private String apiKey;

    private String tradeUrl;
}
