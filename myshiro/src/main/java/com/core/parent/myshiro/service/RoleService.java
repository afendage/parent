package com.core.parent.myshiro.service;

import com.core.parent.myshiro.entity.Role;

import java.util.List;

public interface RoleService {

    List<Role> selectByName(String username);

}
