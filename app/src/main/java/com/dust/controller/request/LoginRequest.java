package com.dust.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    private String publicAddress;

    private String signature;
}
