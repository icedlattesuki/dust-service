package com.dust.service;

import com.dust.dao.UserDao;
import com.dust.dao.domain.UserDO;
import com.dust.util.Web3Utils;
import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Hash;
import org.web3j.utils.Numeric;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public long getNonce(String publicAddress) {
        UserDO userDO = userDao.getByPublicAddress(publicAddress);
        if (userDO == null) {
            userDO = createUser(publicAddress);
        }
        return userDO.getNonce();
    }

    public boolean login(String publicAddress, String signature) {
        UserDO userDO = userDao.getByPublicAddress(publicAddress);
        String digest = Web3Utils.digest("Login Nonce = " + userDO.getNonce());
        String decryptAddress = Web3Utils.recoverAddress(digest, signature);
        if (!decryptAddress.equalsIgnoreCase(publicAddress)) {
            return false;
        }
        userDao.updateNonce(publicAddress, generateNonce());
        return true;
    }

    private UserDO createUser(String publicAddress) {
        UserDO userDO = new UserDO();
        userDO.setPublicAddress(publicAddress);
        userDO.setNonce(generateNonce());
        userDO.setName(publicAddress);
        userDao.insert(userDO);
        return userDO;
    }

    private long generateNonce() {
        Random random = new Random();
        return random.nextLong(10000);
    }
}
