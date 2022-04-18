package com.dust.dao;

import com.dust.dao.domain.SteamDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SteamDao {

    @Select("select * from steam where user_uuid=#{userUuid}")
    @Result(column = "user_uuid", property = "userUuid")
    @Result(column = "steam_id", property = "steamId")
    @Result(column = "api_key", property = "apiKey")
    @Result(column = "trade_url", property = "tradeUrl")
    SteamDO get(@Param("userUuid") String userUuid);
}
