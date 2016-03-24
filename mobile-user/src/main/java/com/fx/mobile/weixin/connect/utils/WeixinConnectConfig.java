package com.fx.mobile.weixin.connect.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class WeixinConnectConfig {
 private static Properties props=new Properties();
 public static String getValue(String key)
 {
   return props.getProperty(key);
 }

 public static void updateProperties(String key, String value)
 {
   props.setProperty(key, value);
 }

 static
 {
   try
   {
     props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("weixinconnectconfig.properties"));
   } catch (FileNotFoundException e) {
     e.printStackTrace();
   } catch (IOException e) {
     e.printStackTrace();
   }
 }
}
