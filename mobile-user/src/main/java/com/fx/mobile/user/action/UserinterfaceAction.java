package com.fx.mobile.user.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;




import com.fx.mobile.model.NoteNumbRecord;
import com.fx.mobile.model.UserOperate;
import com.fx.mobile.user.service.UserinterfaceService;
import com.fx.mobile.user.util.InterfaceClass;

@Controller("userinterfaceAction")

public class UserinterfaceAction extends BaseAction {
	public String imageHttp;
	
//	public String imageHttp=UrlProperties.getFileHttpUrl();//获取资源服务器地址，，，做拼接
	
	@Autowired
	public UserinterfaceService userinterfaceService;
	
	InterfaceClass interfaceClass = new InterfaceClass();
	
		//String openId = "d500e1c800aa94d6e651f64b77ea56f8";
		
		// 根据用户端保存的tokenId和openId登录----------是否有效并返回用户基本信息-------------------------------------------------------------
		public void findUserInfo() throws IOException{
			String openId = this.getRequest().getParameter("openId");// openId
			StringBuffer sb = new StringBuffer();
			try{
					// 调用业务，判断用户是否存在
					List<UserOperate> listResult = userinterfaceService.findUserOperateByOpenId(openId);
					if (listResult.size()>0) {
						sb.append("{\"msg\":\""); // 检查用户返回结果
						sb.append("001");
						// sb.append(",");
						for(UserOperate uo : listResult){//分装用户信息返回
							//JSONObject jsonObject = JSONObject.fromObject(uo);  //将所有用户信息封装成json数据   
							//System.out.println( jsonObject );  
							sb.append("\",\"userName\":\"");  
			                sb.append(uo.getNickname());//昵称  
			                sb.append("\",\"sex\":\"");  
			                sb.append(uo.getSex()); 
			                sb.append("\",\"portraitURL\":\"");  //头像
			                if(null==uo.getFigureurl()||"".equals(uo.getFigureurl())){
			                	sb.append(uo.getFigureurl()); 
			                }else{
			                	sb.append(imageHttp+uo.getFigureurl()); 
			                }
			                sb.append("\",\"province\":\"");  
			                sb.append(uo.getProvince()); 
			                sb.append("\",\"city\":\"");  
			                sb.append(uo.getCity()); 
			                sb.append("\",\"birthday\":\"");  
			                sb.append(uo.getBirthday()); 
			                sb.append("\",\"devinfo\":\"");  //设备信息
			                sb.append(uo.getDevinfo()); 
			                sb.append("\",\"userinfo\":\"");  
			                sb.append(uo.getUserinfo()); 
			                String itegr = "";
//			                String itegr = interfaceClass.getMarketIntegralJson(openId);//调用运用系统中的接口方法
			                sb.append("\",\"integral\":\"");//积分
			                sb.append(itegr);//=====================================积分暂时未获取，暂时默认给0
			                sb.append("\",\"phicommId\":\"");  
			                sb.append(uo.getPhicommId()+"\""); 
						}
						sb.append("}");
					}else{
						sb.append("{\"msg\":\""); // 检查用户返回结果
						sb.append("000\"");//登录失败
						// sb.append(",");
						sb.append("}");
					}
			}catch (Exception e) {
				log.error(e.getMessage());
				sb.append("{\"msg\":\""); // 检查用户返回结果  ，异常
				sb.append("005\"");
				// sb.append(",");
				sb.append("\"}");
			}
			System.out.println("检查用户返回json=" + sb);
			PrintWriter out = this.getResponse().getWriter();
			out.print(sb.toString());
			out.flush();
			out.close();
	}
		
		//查找已使用的短信个数
		@SuppressWarnings("unchecked")
		public String findNoteNumb(){
//		  try{
//			  List<NoteNumbRecord> list = userinterfaceService.findNoteNumb();
//			  String count ="0";
//			  if(list.size()>0){
//				  count=String.valueOf(list.get(0).getNodeNumb());  
//				  this.resultMap.put("noteNumbRecord", count);
//			  }else{
//				  this.resultMap.put("noteNumbRecord", "0");
//			  }
//		  }catch (Exception e) {
//			  this.resultMap.put("noteNumbRecord", "获取异常");
//			  log.info("获取短信数异常");
//		  }
		  
		  return "jsonObject";
		}
		

}
