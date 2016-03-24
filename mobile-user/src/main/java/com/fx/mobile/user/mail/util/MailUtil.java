package com.fx.mobile.user.mail.util;

import java.util.regex.Pattern;

public class MailUtil {
//  public static boolean isMailAddress(String mail){
//	  
//  }
  public static boolean isVaildEmail(String email){     
	  String emailPattern="[a-zA-Z0-9][a-zA-Z0-9._-]{2,16}[a-zA-Z0-9]@[a-zA-Z0-9]+.[a-zA-Z0-9]+";     
	  boolean result=Pattern.matches(emailPattern, email);      
	  return result; }
}
