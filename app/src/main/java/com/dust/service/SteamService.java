package com.dust.service;

import com.dust.dao.SteamDao;
import com.dust.dao.domain.SteamDO;
import com.dust.service.entity.SteamInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SteamService {

    @Autowired
    private SteamDao steamDao;

    public SteamInfo getSteamInfo(String userUuid) {
        return map(steamDao.get(userUuid));
    }

    private SteamInfo map(SteamDO steamDO) {
        if (steamDO == null) {
            return null;
        }
        SteamInfo steamInfo = new SteamInfo();
        steamInfo.setUserUuid(steamDO.getUserUuid());
        steamInfo.setSteamId(steamDO.getSteamId());
        steamInfo.setApiKey(steamDO.getApiKey());
        steamInfo.setTradeUrl(steamDO.getTradeUrl());
        return steamInfo;
    }
}
