package com.core.parent.myshiro;

import com.core.parent.myshiro.entity.User;
import com.core.parent.myshiro.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShiroWebTest {

    @Autowired
    private UserService userService;

    @Test
    public void selectTest(){
        User user = userService.selectByName("zhang");
        System.out.println(user.getUsername()+ "|" +user.getPassword());
    }

}
