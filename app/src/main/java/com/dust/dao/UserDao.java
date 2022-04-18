package com.dust.dao;

import com.dust.dao.domain.UserDO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDao {

    @Insert("insert into user (uuid, public_address, nonce) values (#{uuid}, #{publicAddress}, #{nonce})")
    int insert(UserDO user);

    @Select("select * from user where uuid = #{userUuid}")
    @Result(column = "public_address", property = "publicAddress")
    UserDO getByUuid(@Param("userUuid") String userUuid);

    @Select("select * from user where public_address = #{publicAddress}")
    @Result(column = "public_address", property = "publicAddress")
    UserDO getByPublicAddress(String publicAddress);

    @Update("update user set nonce = #{nonce} where public_address = #{publicAddress}")
    int updateNonce(@Param("publicAddress") String publicAddress, @Param("nonce") long nonce);
}
