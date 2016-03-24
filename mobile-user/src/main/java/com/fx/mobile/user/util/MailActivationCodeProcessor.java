package com.fx.mobile.user.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MailActivationCodeProcessor {
	public static MailActivationCodeProcessor instance=new MailActivationCodeProcessor();
	public long previous; 
	private MailActivationCodeProcessor(){}
	public static MailActivationCodeProcessor getInstance(){
		 return instance;
	}
	   public synchronized String generateToken(String msg, boolean timeChange) {   
	        try {   
	  
	            long current = System.currentTimeMillis();   
	            previous = current;    
	            MessageDigest md = MessageDigest.getInstance("MD5");   
	            md.update(msg.getBytes());   
	            if (timeChange) {   
	                // byte now[] = (current+"").toString().getBytes();   
	                byte now[] = (new Long(current)).toString().getBytes();   
	                md.update(now);  
	            }   
	            return toHex(md.digest());   
	        } catch (NoSuchAlgorithmException e) {   
	            return null;   
	        }   
	    }   
	    //a40285ff60d816a8bae4ffeb999457fb af1c57971f9da55ca2ebe2b0573026eb
	 
	    private String toHex(byte buffer[]) {   
	        StringBuffer sb = new StringBuffer(buffer.length * 2);   
	        for (int i = 0; i < buffer.length; i++) {   
	            sb.append(Character.forDigit((buffer[i] & 240) >> 4, 16));   
	            sb.append(Character.forDigit(buffer[i] & 15, 16));   
	        }   
	  
	        return sb.toString();   
	    } 
}
