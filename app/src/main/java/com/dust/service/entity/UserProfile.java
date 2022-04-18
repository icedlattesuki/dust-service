package com.dust.service.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfile {

    private String userUuid;

    private String publicAddress;

    private String name;

    private String avatarKey;

    private String avatarUrl;
}
