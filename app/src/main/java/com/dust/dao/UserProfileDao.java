package com.dust.dao;

import com.dust.dao.domain.UserProfileDO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserProfileDao {

    @Insert("insert into user_profile (user_uuid, name, avatar_key) values (#{userUuid}, #{name}, #{avatarKey})")
    int insert(UserProfileDO profileDO);

    @Select("select * from user_profile where user_uuid=#{userUuid}")
    @Result(column = "user_uuid", property = "userUuid")
    @Result(column = "avatar_key", property = "avatarKey")
    UserProfileDO get(@Param("userUuid") String userUuid);
}
