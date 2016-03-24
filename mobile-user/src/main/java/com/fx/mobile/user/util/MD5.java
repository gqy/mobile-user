package com.fx.mobile.user.util;



import com.fx.mobile.util.MD5Util;



public class MD5 {
    /**

     * 
     * @param password
     * @return
     */
    public static String getMD5Passwored(String password) {
    	return MD5Util.encodeByMD5(password).substring(0, 24);
    
    }
public static void main(String[] args) {
	System.out.println(getMD5Passwored("123456"));
}

}
