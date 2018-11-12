package com.core.parent.myshiro.mapper;

import com.core.parent.myshiro.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper {

    @Select("select * from user_roles where username=#{username}")
    List<Role> selectByName(String username);

}
