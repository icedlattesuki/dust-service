package com.dust.service.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserBasic {

    private String userUuid;

    private String username;

    private String publicAddress;

    private String avatarUrl;
}
