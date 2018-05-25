package com.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import sun.misc.BASE64Encoder;

public class md5 {
	
	@Test
	public void test() {
		String md5Str = this.md5_encode("123456");
		System.out.println(md5Str);

	}

	public String md5_encode(String str) {
		String s = str;
		if (s == null) {
			return "";
		} else {
			String value = null;
			MessageDigest md5 = null;
			try {
				md5 = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			BASE64Encoder baseEncoder = new BASE64Encoder();
			try {
				value = baseEncoder.encode(md5.digest(s.getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return value;
		}
	}
}
