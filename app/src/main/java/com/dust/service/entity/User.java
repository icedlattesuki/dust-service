package com.dust.service.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private long id;

    private String publicAddress;

    private long nonce;

    private String name;
}
