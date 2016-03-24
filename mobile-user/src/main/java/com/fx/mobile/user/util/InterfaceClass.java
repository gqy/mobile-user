package com.fx.mobile.user.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;



public class InterfaceClass {
//	
//	public String markeHttpUrl= UrlProperties.getMarketHttpUrl();
//	
//	//调用http接口（调用用户管理系统中的接口）
//	public String getMarketIntegralJson(String urlpath) throws IOException{
//		
//		// 1.构造HttpClient的实例
//        HttpClient httpClient = new HttpClient();
//
//        // 用于测试的http接口的url
//        //String path=markeHttpUrl+"/marketing/interfaces!findUserIntegrals.action?openId="+openId;
//        
//        // 拼凑get请求的URL字串，使用URLEncoder.encode对特殊和不可见字符进行编码
//        URL getUrl = new URL(urlpath);
//        // 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型，
//        // 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
//        HttpURLConnection connection = (HttpURLConnection) getUrl
//                .openConnection();
//        // 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到
//        // 服务器
//        connection.connect();
//        // 取得输入流，并使用Reader读取
//        BufferedReader reader = new BufferedReader(new InputStreamReader(
//                connection.getInputStream()));
//        String jsonstr ="";
//        String lines;
//        while ((lines = reader.readLine()) != null) {
//            System.out.println(lines);
//            jsonstr=lines;
//        }
//        reader.close();
//        // 断开连接
//        connection.disconnect();
//        System.out.println("==="+jsonstr);
//        //将json数据解析
//        JSONObject jsonObject = JSONObject.fromObject(jsonstr);
//        Iterator it=jsonObject.keys();
//        String s ="";
//        while (it.hasNext()){
//        	s = String.valueOf(jsonObject.get(it.next()));//获取到用户总积分
//        	//System.out.println(jsonObject.get(it.next()));
//        }
//		return s;
//	}

}
