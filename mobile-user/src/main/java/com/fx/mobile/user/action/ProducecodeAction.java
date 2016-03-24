package com.fx.mobile.user.action;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fx.mobile.model.UserOperate;
import com.fx.mobile.user.constants.enums.UserStatusEnum;
import com.fx.mobile.user.dao.UserOperateDao;
import com.fx.mobile.user.service.NoteNumRecordService;
import com.fx.mobile.user.service.UserOperateService;
import com.fx.mobile.bean.JSONResult;


@Controller
@RequestMapping(value = "/producecode")
public class ProducecodeAction extends BaseAction {

	
	@Autowired
	private UserOperateService operateService;
	
	@Autowired
	public UserOperateDao userOperateDao;
	
	@Autowired
	private NoteNumRecordService noteNumRecordService;
	
	private boolean isdebug = false;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public  DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/***
	 * 用户重置密码时 发送验证码
	 * */
	@RequestMapping(value = "/sendCodeForResetPwd", method = { RequestMethod.POST})
	@ResponseBody
	public JSONResult sendCodeForResetPwd(HttpServletRequest request){
		JSONResult jsonResult = new JSONResult();
		Map<String,String> resultMap = new HashMap<String,String>();
		
		try{
			// 手机号码
			String userPhoneNumb = request.getParameter("userPhoneNumb");
			
			if (userPhoneNumb == null||userPhoneNumb == "" ) {
				jsonResult.setStatus("000");
				jsonResult.setDesc("手机号码为空");
			
			} 
			else {
				UserOperate user = new UserOperate();
				user.setUserPhoneNumb(userPhoneNumb);
				// 调用业务，判断用户是否存在
				String listResult = operateService.findUsers(user);// 返回000说明用户不存在、001说明用户已存在
				
				if ("000".equals(listResult)) {
					jsonResult.setStatus("000");
					jsonResult.setDesc("当前手机号码不存在");
					
					
				}else{
					String message ="您的手机正在找回密码";
					int codestatu = httpPost(request ,userPhoneNumb,message);//调用发送短信方法请求数据
					log.info("调用http方法后返回状态码："+codestatu);
					
					String sessionids = request.getSession().getId();
					if(codestatu==200){//发送短信成功
						jsonResult.setStatus("001");
						jsonResult.setDesc("发送成功");
						jsonResult.setData(userOperateDao.findUsersByPhone(user).get(0));
						resultMap.put("phicommId",userOperateDao.findUsersByPhone(user).get(0).getPhicommId());
						// resultMap.put("sessionId",sessionids);		
					 }else{
						jsonResult.setStatus("000");
						jsonResult.setDesc("发送短信失败");
					 }
				}
			}
		}catch(Exception e){
			log.info(e.getMessage());
			jsonResult.setStatus("-2");
			jsonResult.setDesc("系统异常");
			
		}
		jsonResult.setData(resultMap);
		
		return jsonResult;
		
	}
	
	
	/**
	 * 产生验证码并保存在session中
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/produceCode", method = { RequestMethod.POST})
	@ResponseBody
	public JSONResult produceCode(HttpServletRequest request) throws IOException {
		String result = "0";
		request.setCharacterEncoding("utf-8");
		// 手机号码
		String userPhoneNumb = request.getParameter("userPhoneNumb");
		log.debug("userPhoneNumb:------------------------------------------------------"+userPhoneNumb+"..."+request.getParameter("userPhoneNumb"));
		JSONResult jsonResult = new JSONResult();
		Map<String,String> retMap = new HashMap<String,String>();
		if (userPhoneNumb == null || userPhoneNumb == ""  ) {
	
			jsonResult.setStatus(UserStatusEnum.VALIDATE_CODE_ERR.getCode());
			jsonResult.setDesc(UserStatusEnum.VALIDATE_CODE_ERR.getDescription());
		
		} else {//用户手机不为空执行
			UserOperate user = new UserOperate();
			user.setUserPhoneNumb(userPhoneNumb);
			// 调用业务，判断用户是否存在
			String listResult = operateService.findUsers(user);// 返回000说明用户不存在、001说明用户已存在
			
			if (UserStatusEnum.USER_EXISTED.getCode().equals(listResult)) {//用户手机已注册过执行
				// 得到集合的长度
				jsonResult.setStatus(listResult);
				jsonResult.setDesc( UserStatusEnum.USER_EXISTED.getDescription());
			}else{//用户手机可以使用执行
				//-------------------------------------------------------------------------调用方法
				String message ="您的手机正在注册斐讯账号";
				System.out.println("2-++++++++++++++++++++++++++++++++++++++"+"beforesend");
				int codestatu = httpPost(request,userPhoneNumb,message);//调用发送短信方法请求数据
//				int codestatu = 200;
				
				System.out.println("3-++++++++++++++++++++++++++++++++++++++"+"aftersend");

				System.out.println("调用http方法后返回状态码："+codestatu);
				String sessionids = request.getSession().getId();
				System.out.println("sessionid："+sessionids);
				if(codestatu==200){//发送短信成功
					 jsonResult.setStatus( UserStatusEnum.VALIDATE_CODE_SUC.getCode());
					 jsonResult.setDesc( UserStatusEnum.VALIDATE_CODE_SUC.getDescription());
					 retMap.put("sessionId", sessionids);
				 }else{
					 log.debug("发送失败");
					 jsonResult.setStatus(UserStatusEnum.VALIDATE_CODE_ERR.getCode());
					 jsonResult.setDesc(UserStatusEnum.VALIDATE_CODE_ERR.getDescription());
				
				 }
				// sb.append("]");
			}
		}
		jsonResult.setData(retMap);
		return jsonResult;

	}
	
	//发送短信验证码方法------------------------------------------------------------
	public int httpPost(HttpServletRequest request,String userPhoneNumb,String message) throws HttpException, IOException{
		//String http = "http://222.73.117.158/msg/HttpSendSM?Account=cs-012&pswd=Tch123123&mobile="+userPhoneNumb+"&msg=PHICOMM regist code"+code+"&needstatus=true";
		// 产生验证码6位1-9随机数
		int codes = (int) ((Math.random() * 9 + 1) * 100000);

		String code = String.valueOf(codes);
		int statusCode=0;//请求结构200成功，否则失败
		String result = "";//响应成功后，内容
		if(isdebug){
			String testCode = "123456";
			request.getSession().setAttribute("code", testCode);
			request.getSession().setAttribute("userPhoneNumb", userPhoneNumb);
			request.getSession().setAttribute("codeTime", df.format(new Date()));//过期时间保存
			return 200;
		}
		// 短信验证码存入session
		HttpClient client  = new HttpClient();//定义http客户端对象httpClient
		String url = "http://222.73.117.158/msg/HttpSendSM";
		PostMethod postMethod = new PostMethod(url);//定义并实例化客户端连接对象 postMethod
		try {
			//设置字符编码
			postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			//设置请求超时5s
			//postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
			//设置请求重试
			//postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			//设置http的头
			postMethod.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");
			//参数
			NameValuePair [] data ={new NameValuePair("account","feixun_RDmarketing"),
									new NameValuePair("pswd","MarKeting1$"),
									new NameValuePair("mobile",userPhoneNumb),
					                new NameValuePair("msg",message+",效验码："+code+"请不要告诉别人哦！"),
					                new NameValuePair("needstatus","true")
			                        };
			//将值放入postMethod中
			postMethod.setRequestBody(data);
			//定义访问地址连接状态
			try {
				//客户端请求url 数据
				statusCode = client.executeMethod(postMethod);
				//调用方法记录短信发送的条数
				//statusCode=200;
				log.debug("发送验证码："+code);
				request.getSession().setAttribute("code", code);
				request.getSession().setAttribute("userPhoneNumb", userPhoneNumb);
				request.getSession().setAttribute("codeTime", df.format(new Date()));//过期时间保存
				//statusCode=200;
			} catch (Exception e) {
				e.printStackTrace();
				statusCode=-1;
			}
			//请求状态200成功
			if(statusCode==HttpStatus.SC_OK){
				try {
					result = postMethod.getResponseBodyAsString();//响应内容 ↓
					log.info("http响应内容："+result);
					
				} catch (Exception e) {
					e.printStackTrace();
					statusCode=-1;
				}finally{
					String rest =noteNumRecordService.executeMethod();//调用记录短信条数方法
					log.info("记录短信条数结果(1成功,0失败)："+rest);
				}
			}else{
				statusCode=-1;
				log.error("http请求状态："+statusCode);
			}
		} catch (Exception e) {
			statusCode=-1;
			log.error(e.getMessage(),e);
		}finally{
			//释放连接
			postMethod.releaseConnection();
			client.getHttpConnectionManager().closeIdleConnections(0);
		}
		return statusCode;//这里只返回发送的状态，成功则返回200 ，否则其他-----需要返回发送验证码后响应内容则要发回 result ↑
	}
}
