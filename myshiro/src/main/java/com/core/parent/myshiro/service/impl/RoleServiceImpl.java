package com.core.parent.myshiro.service.impl;

import com.core.parent.myshiro.entity.Role;
import com.core.parent.myshiro.mapper.RoleMapper;
import com.core.parent.myshiro.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> selectByName(String username) {
        return roleMapper.selectByName(username);
    }
}
