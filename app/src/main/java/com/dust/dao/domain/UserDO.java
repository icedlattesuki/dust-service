package com.dust.dao.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDO {

    private long id;

    private String uuid;

    private String publicAddress;

    private long nonce;
}
