package com.dust.service;

import com.dust.dao.UserProfileDao;
import com.dust.dao.UserDao;
import com.dust.dao.domain.UserProfileDO;
import com.dust.dao.domain.UserDO;
import com.dust.exception.UnauthException;
import com.dust.util.JwtUtils;
import com.dust.util.Web3Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserProfileDao userProfileDao;

    public long getNonce(String publicAddress) {
        UserDO userDO = userDao.getByPublicAddress(publicAddress);
        if (userDO == null) {
            userDO = createUser(publicAddress);
        }
        return userDO.getNonce();
    }

    public String login(String publicAddress, String signature) {
        UserDO userDO = userDao.getByPublicAddress(publicAddress);
        String digest = Web3Utils.digest("Login Nonce = " + userDO.getNonce());
        String decryptAddress = Web3Utils.recoverAddress(digest, signature);
        if (!decryptAddress.equalsIgnoreCase(publicAddress)) {
            throw new UnauthException();
        }
        userDao.updateNonce(publicAddress, generateNonce());
        return generateJwt(userDO.getUuid());
    }

    @Transactional
    private UserDO createUser(String publicAddress) {
        UserDO userDO = new UserDO();
        userDO.setUuid(UUID.randomUUID().toString());
        userDO.setPublicAddress(publicAddress);
        userDO.setNonce(generateNonce());
        userDao.insert(userDO);

        UserProfileDO profileDO = new UserProfileDO();
        profileDO.setUserUuid(userDO.getUuid());
        profileDO.setName(publicAddress);
        userProfileDao.insert(profileDO);

        return userDO;
    }

    private long generateNonce() {
        Random random = new Random();
        return random.nextLong(10000);
    }

    private String generateJwt(String userUuid) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("userUuid", userUuid);
        return JwtUtils.createJwt(payload);
    }
}
