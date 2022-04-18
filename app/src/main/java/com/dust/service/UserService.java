package com.dust.service;

import com.dust.dao.UserDao;
import com.dust.dao.UserProfileDao;
import com.dust.dao.domain.UserDO;
import com.dust.dao.domain.UserProfileDO;
import com.dust.infra.ObjectStorageManager;
import com.dust.service.entity.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserProfileDao userProfileDao;

    @Autowired
    private ObjectStorageManager objectStorageManager;

    public UserProfile getProfile(String userUuid) {
        UserProfile userProfile = map(userDao.getByUuid(userUuid), userProfileDao.get(userUuid));
        setAvatarUrl(userProfile);
        return userProfile;
    }

    private UserProfile map(UserDO userDO, UserProfileDO userProfileDO) {
        if (userProfileDO == null) {
            return null;
        }
        UserProfile userProfile = new UserProfile();
        userProfile.setUserUuid(userProfileDO.getUserUuid());
        userProfile.setPublicAddress(userDO.getPublicAddress());
        userProfile.setName(userProfileDO.getName());
        userProfile.setAvatarKey(userProfileDO.getAvatarKey());
        return userProfile;
    }

    private void setAvatarUrl(UserProfile userProfile) {
        if (userProfile == null) {
            return;
        }
        userProfile.setAvatarUrl(objectStorageManager.getObjectUrl(userProfile.getAvatarKey()));
    }
}
