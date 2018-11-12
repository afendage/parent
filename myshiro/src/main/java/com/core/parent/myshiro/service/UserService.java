package com.core.parent.myshiro.service;

import com.core.parent.myshiro.entity.User;

import java.util.List;

public interface UserService {

    List<User> selectAll();

    User selectByName(String username);

}
