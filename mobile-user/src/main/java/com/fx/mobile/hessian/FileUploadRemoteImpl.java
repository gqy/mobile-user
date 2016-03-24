package com.fx.mobile.hessian;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;







import org.apache.commons.logging.LogFactory;

import com.fx.marketing.hessian.IFileUploadRemote;
import com.fx.mobile.user.service.ResourceProperties;


public class FileUploadRemoteImpl implements IFileUploadRemote {
	
	 
	
	static Log log = LogFactory.getLog(FileUploadRemoteImpl.class);
		
		public void deleteFile(String oldAppUrl){
			
			 String sPath =((UrlProperties) WebConfig.getBean("urlProperties")).getFilepath();
			 System.out.println(sPath);
			 
			 File fl = new File(sPath+oldAppUrl);
			 
			 if(fl.exists()){
				 fl.delete();
				// System.out.println("删除成功");
			 }
			
		}
		/** 参数描述
		 * byte[]    : 文件字节数组
		 * path      : /upload/activity/icon480/GIXLGYGIEL1
		 * filename  : xxxx.jpg 
		 * */
		public String upload(byte[] bytes,String path,String filename) {
			FileOutputStream fos=null;
			try{
			//	String sPath = "/root/apache-tomcat-7.0.53/webapps/marketFile";
			//  String path  ="/upload/activity/icon1080/"+datedir+"/"+randomdir+"/" ;
				//filename= "1.jpg";
				log.info("path="+path+",filename="+filename);
				String sPath =((ResourceProperties) WebConfig.getBean("resourceProperties")).getFileHttpUrl();
				
				 File fl = new File(sPath+path);
		             if(!fl.isDirectory())      
		             {       
		                 fl.mkdirs();    
		             }
		             
		             File fe = new File(sPath+"/"+path+filename);
		             if(fe.exists())      
		             {       
		                 fe.delete();    
		             }
		             if (!fe.getParentFile().exists()) {
		            	 fe.getParentFile().mkdirs();   
		     		}
		           
		         fos = new FileOutputStream(fe);
		         fos.write(bytes);
		         
		       //String host="http://172.17.100.113/marketFile";
		 		String url = ((ResourceProperties) WebConfig.getBean("resourceProperties")).getRemoteFileUrl();
		 		//返回：http://172.17.100.113/marketing/upload/activity/icon480/GIXLGYGIEL1/xxx.jpg
		 		log.info(url+"/"+path+filename);
		 		
		 		//return url+"/"+path+filename;
		 		return path+filename;
		 		
			}catch(Exception e){
				e.printStackTrace();
				return "";
			}finally{
				try {
					if(fos!= null){
						fos.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
					log.error(e, e);
				}
				
			}
				 
		}

		public String test(String name) {
			return "333";
		}

		
	
	}
