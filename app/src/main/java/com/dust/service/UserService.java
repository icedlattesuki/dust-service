package com.dust.service;

import com.dust.dao.UserDao;
import com.dust.dao.UserProfileDao;
import com.dust.dao.domain.UserDO;
import com.dust.dao.domain.UserProfileDO;
import com.dust.infra.ObjectStorageManager;
import com.dust.service.entity.User;
import com.dust.service.entity.UserBasic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserProfileDao userProfileDao;

    @Autowired
    private ObjectStorageManager objectStorageManager;

    private HttpClient httpClient;

    public UserService() {
        httpClient = HttpClient.newHttpClient();
    }

    public UserBasic getBasic(String userUuid) {
        return getUser(userUuid);
    }

    public User getUser(String userUuid) {
        return map(userDao.getByUuid(userUuid), userProfileDao.get(userUuid));
    }

    public String updateAvatar(String userUuid, String avatarKey) {
        userProfileDao.updateAvatar(userUuid, avatarKey);
        return objectStorageManager.getObjectUrl(avatarKey);
    }

    public void updateUsername(String userUuid, String username) {
        userProfileDao.updateUsername(userUuid, username);
    }

    public String bindSteamId(String userUuid, String steamReturnUrl) {
        try {
            if (!verifySteamReturnUrl(steamReturnUrl)) {
                return null;
            }
            String steamId = getSteamId(steamReturnUrl);
            if (StringUtils.hasText(steamId)) {
                userProfileDao.updateSteamId(userUuid, steamId);
            }
            return steamId;
        } catch (Exception e) {
            LOGGER.error("fail to bind steam id", e);
            return null;
        }
    }

    public void updateSteamApiKey(String userUuid, String steamApiKey) {
        userProfileDao.updateSteamApiKey(userUuid, steamApiKey);
    }

    public void updateSteamTradeUrl(String userUuid, String steamTradeUrl) {
        userProfileDao.updateSteamTradeUrl(userUuid, steamTradeUrl);
    }

    private User map(UserDO userDO, UserProfileDO userProfileDO) {
        if (userDO == null || userProfileDO == null) {
            return null;
        }
        User user = new User();
        user.setUserUuid(userDO.getUuid());
        user.setPublicAddress(userDO.getPublicAddress());
        user.setUsername(userProfileDO.getName());
        user.setAvatarUrl(getAvatarUrl(userProfileDO.getAvatarKey()));
        user.setSteamId(userProfileDO.getSteamId());
        user.setApiKey(userProfileDO.getApiKey());
        user.setTradeUrl(userProfileDO.getTradeUrl());
        return user;
    }

    private String getAvatarUrl(String avatarKey) {
        if (avatarKey == null) {
            return null;
        }
        return objectStorageManager.getObjectUrl(avatarKey);
    }

    private boolean verifySteamReturnUrl(String steamReturnUrl) throws IOException, URISyntaxException, InterruptedException {
        String verifyParams = steamReturnUrl.split("\\?")[1];
        verifyParams = verifyParams.replace("openid.mode=id_res", "openid.mode=check_authentication");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://steamcommunity.com/openid/login?" + verifyParams))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body().contains("is_valid:true");
    }

    private String getSteamId(String steamReturnUrl) {
        String[] params = steamReturnUrl.split("\\?")[1].split("&");
        for (String param : params) {
            if (param.startsWith("openid.claimed_id")) {
                String[] tmp = param.split("%2F");
                return tmp[tmp.length - 1];
            }
        }
        return null;
    }
}
