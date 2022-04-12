package com.dust.dao;

import com.dust.dao.domain.UserDO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDao {

    @Insert("insert into user (public_address, nonce, name) values (#{publicAddress}, #{nonce}, #{name})")
    int insert(UserDO user);

    @Select("select * from user where public_address = #{publicAddress}")
    UserDO getByPublicAddress(String publicAddress);

    @Update("update user set nonce = #{nonce} where public_address = #{publicAddress}")
    int updateNonce(@Param("publicAddress") String publicAddress, @Param("nonce") long nonce);
}
