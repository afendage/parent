package com.core.parent.myshiro.mapper;

import com.core.parent.myshiro.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from users")
    List<User> selectAll();

    @Select("select * from users where username=#{username}")
    User selectByName(String username);
}
