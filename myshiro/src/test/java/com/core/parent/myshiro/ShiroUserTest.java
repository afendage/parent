package com.core.parent.myshiro;

import com.core.parent.myshiro.system.utils.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShiroUserTest {

    @Before
   public void before(){

   }

   @Test
   public void testLogin(){
      Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:config/shiro.ini");
      SecurityManager securityManager = factory.getInstance();
      SecurityUtils.setSecurityManager(securityManager);
      Subject subject = SecurityUtils.getSubject();
      UsernamePasswordToken usernamePasswordToken= new UsernamePasswordToken("zhang","123");
      try {
         subject.login(usernamePasswordToken);
         Assert.assertEquals(true,subject.isAuthenticated());
         System.out.println("验证登录成功");
      } catch (AuthenticationException e) {
         System.out.println("登录失败");
         e.printStackTrace();
      }
      subject.logout();

   }

   @Test
   public void testRealm(){
      Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:config/realm.ini");
      SecurityManager manager = factory.getInstance();
      SecurityUtils.setSecurityManager(manager);
      Subject subject = SecurityUtils.getSubject();
      UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("liu","456");
      try {
         subject.login(usernamePasswordToken);
         Assert.assertEquals(true,subject.isAuthenticated());
         System.out.println("登录成功");
      } catch (AuthenticationException e) {
         System.out.println("登录失败");
         e.printStackTrace();
      }
      subject.logout();
   }

   @Test
   public void testJdbcRealm() throws Exception {
      ShiroUtil.login("classpath:config/jdbcRealm.ini","zhang","123");
      Subject subject = SecurityUtils.getSubject();
      Assert.assertEquals(true,subject.isAuthenticated());
   }

   @Test
   public void testStrategy() throws Exception{
       ShiroUtil.login("classpath:config/strategy.ini","liu","456");
       Subject subject = SecurityUtils.getSubject();
        //得到一个身份集合，其包含了Realm验证成功的身份信息
       PrincipalCollection principalCollection = subject.getPrincipals();
       Assert.assertEquals(2, principalCollection.asList().size());
   }

}
