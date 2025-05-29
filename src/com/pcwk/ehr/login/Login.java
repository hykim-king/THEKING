package com.pcwk.ehr.login;

import java.util.Scanner;

public class Login {
	
	static void LoginService() {
		Scanner s = new Scanner(System.in);
		
		System.out.println("아이디를 입력하세요.");
		String id = s.nextLine();
	}
	public static void main(String[] args) {
		System.out.println("Login 페이지");
		System.out.println("LOGIN PAGE UPDATE DAY3!");
		System.out.println("LOGIN PAGE UPDATE DAY4!");
		
		LoginService();
	}
	
}
