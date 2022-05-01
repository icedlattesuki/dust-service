package com.dust.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;


@Getter
@Setter
public class UpdateUsernameRequest {

    @Pattern(regexp = "^[\u4E00-\u9FA5A-Za-z0-9]{4,14}$", message = "用户名不符合规则")
    private String username;
}
