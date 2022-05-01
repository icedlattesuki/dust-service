package com.dust.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class UpdateSteamTradeUrlRequest {

    @Pattern(regexp = "^[A-Z0-9]{32}$", message = "Steam交易链接不符合规则")
    private String steamTradeUrl;
}
