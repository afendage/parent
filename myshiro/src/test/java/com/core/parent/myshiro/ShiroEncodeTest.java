package com.core.parent.myshiro;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.Key;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShiroEncodeTest {

    //base64 编码/解码操作
    @Test
    public void Base64Test(){
        String str = "hello";
        String base64Encode = Base64.encodeToString(str.getBytes());
        String str2 = Base64.decodeToString(base64Encode);
        System.out.println(str.equals(str2));
    }

    //16进制字符串编码/解码
    @Test
    public void HexTest(){
        String str = "hello";
        String encode = Hex.encodeToString(str.getBytes());
        String str2 = new String(Hex.decode(encode.getBytes()));
        System.out.println(str.equals(str2));
    }

    //MD5 散列“hello”。另外散列时还可以指定散列次数，如2 次表
    //示：md5(md5(str))：“new Md5Hash(str, salt, 2).toString()”
    public void md5Test(){
        String str = "hello";
        String salt = "123";
        String md5 = new Md5Hash(str,salt).toString(); ////还可以转换为toBase64()/toHex()
        System.out.println(md5);
        //使用SHA256 算法生成相应的散列数据
        String sha1 = new Sha256Hash(str,salt).toString();
        System.out.println(sha1);
    }

    //AES 算法实现
    @Test
    public void AECTest(){
        AesCipherService aesCipherService = new AesCipherService();
        aesCipherService.setKeySize(128); //设置key长度
        Key key = aesCipherService.generateNewKey();//生成key
        String text = "hello";
        String encrptText = aesCipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();//加密
        String text2 =  new String(aesCipherService.decrypt(Hex.decode(encrptText), key.getEncoded()).getBytes());//解密
        Assert.assertEquals(text, text2);
    }


}
