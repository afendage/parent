package com.core.parent.myshiro.system.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

public class MyRealm1 implements Realm {
    @Override
    public String getName() {
        return "myRealm1";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
       String username = (String)token.getPrincipal();
       String password = new String((char[]) token .getCredentials());
        if(!"liu".equals(username)){
            throw new UnknownAccountException(); //如果用户名错误
        }
        if(!"456".equals(password)){
            throw new IncorrectCredentialsException();//如果密码错误
        }
        return new SimpleAuthenticationInfo(username,password,getName());
    }
}
