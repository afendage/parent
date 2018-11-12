package com.core.parent.myshiro.service;

import com.core.parent.myshiro.entity.Permission;

import java.util.List;

public interface PermissionService {

    List<Permission> selectByName(String roleName);

}
