package com.core.parent.myshiro.mapper;

import com.core.parent.myshiro.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PermissionMapper {

    @Select("select * from roles_permissions where role_name=#{roleName}")
    List<Permission> selectByName(String roleName);

}
