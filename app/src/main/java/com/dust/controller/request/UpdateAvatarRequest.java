package com.dust.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class UpdateAvatarRequest {

    @Pattern(regexp = "^avatar/[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}\\.(?:jpg|jpeg|png)$", message = "Avatar Key不符合规则")
    private String avatarKey;
}
