package com.core.parent.myshiro.service.impl;

import com.core.parent.myshiro.entity.Permission;
import com.core.parent.myshiro.mapper.PermissionMapper;
import com.core.parent.myshiro.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;


    @Override
    public List<Permission> selectByName(String roleName) {
        return permissionMapper.selectByName(roleName);
    }
}
