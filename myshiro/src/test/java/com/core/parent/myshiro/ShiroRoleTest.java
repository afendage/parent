package com.core.parent.myshiro;

import com.core.parent.myshiro.system.utils.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShiroRoleTest {

    @Test
    public void testRole() throws Exception{
        ShiroUtil.login("classpath:config/role.ini","zhang","123");
        Subject subject = SecurityUtils.getSubject();
        System.out.println(subject.hasRole("role1")); ///判断拥有角色：role1
        System.out.println(subject.hasAllRoles(Arrays.asList("role1","role2")));
        if(subject.hasRole("role1")){
            System.out.println("role1 yes!");
        }
        if(subject.hasRole("role2")){
            System.out.println("role2 yes!");
        }
    }

    @Test
    public void testRole1() throws Exception{
        ShiroUtil.login("classpath:config/role.ini","den","123");
        Subject subject = SecurityUtils.getSubject();
        System.out.println(subject.isPermitted("user:create"));
        System.out.println(subject.isPermittedAll("user:create","user:delete"));
        System.out.println(subject.isPermittedAll("user:create","user:delete","user:update"));
        // subject.checkPermissions("user:delete", "user:update");
    }

}
