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
    @Result(column = "steam_id", property = "steamId")
    @Result(column = "api_key", property = "apiKey")
    @Result(column = "trade_url", property = "tradeUrl")
    UserProfileDO get(@Param("userUuid") String userUuid);

    @Update("update user_profile set avatar_key=#{avatarKey} where user_uuid=#{userUuid}")
    int updateAvatar(@Param("userUuid") String userUuid, @Param("avatarKey") String avatarKey);

    @Update("update user_profile set name=#{username} where user_uuid=#{userUuid}")
    int updateUsername(@Param("userUuid") String userUuid, @Param("username") String username);

    @Update("update user_profile set steam_id=#{steamId} where user_uuid=#{userUuid}")
    int updateSteamId(@Param("userUuid") String userUuid, @Param("steamId") String steamId);

    @Update("update user_profile set steam_api_key=#{steamApiKey} where user_uuid=#{userUuid}")
    int updateSteamApiKey(@Param("userUuid") String userUuid, @Param("steamApikey") String steamApiKey);

    @Update("update user_profile set steam_trade_url=#{steamTradeUrl} where user_uuid=#{userUuid}")
    int updateSteamTradeUrl(@Param("userUuid") String userUuid, @Param("steamTradeUrl") String steamTradeUrl);
}
