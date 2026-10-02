package com.ktdsuniversity.edu.commons.crypto;

import com.ktdsuniversity.edu.commons.crypto.encrypt.hash.SHA;

public class Test {
	
	private static final String AES_SECRET_KEY = "abcde12345abcde12345abcde12345ff";
	
	
	public static void testSHA() {
		String rawPassword = "password1234";
		
		//SHA를 이용한 이중 암호화
		//1. 이중암호화를 위한 SALT 발급.
		//실행을 할때마다 매번다른 SALT가 나온다
		String salt = SHA.generateSalt();
		System.out.println(salt);
		//2. rawPassword와 SALT를 이용한 암호화
		String encryptedPassword = SHA.getEncrypt(rawPassword, salt);
		System.out.println(encryptedPassword);
		
	}
	
	public static void testAESEnc() {
		//AES 암호화 
		String name = "이태훈";
		String encryptedName = AES.encode(AES_SECRET_KEY, name);
		System.out.println(encryptedName);
	}
	
	public static void testAESDec() {
		//AES 복호화
		String encryptedName = "9983877c6df5f5c59c462aa03c6207d3";
		String decryptedName = AES.decode(AES_SECRET_KEY, encryptedName);
		System.out.println(decryptedName);
	}
	
	public static void main(String[] args) {
		
		testSHA();
		testAESEnc();
		testAESDec();
	}

}
