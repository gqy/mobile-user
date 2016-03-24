package com.fx.mobile.user.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fx.marketing.hessian.IFileUploadRemote;
import com.fx.mobile.model.UserOperate;
import com.fx.mobile.user.constants.enums.UserStatusEnum;
import com.fx.mobile.user.dao.UserOperateDao;
import com.fx.mobile.user.service.ResourceProperties;
import com.fx.mobile.user.service.UserOperateService;
import com.fx.mobile.user.util.InterfaceClass;
import com.fx.mobile.user.util.MD5;
import com.fx.mobile.user.util.TokenProcessor;
import com.fx.mobile.bean.JSONResult;

import Decoder.BASE64Decoder;

@Controller
@RequestMapping(value = "/useroperate")
public class UseroperateAction extends BaseAction {

	private static final String ERRORSTATUS = "000";



	InterfaceClass interfaceClass = new InterfaceClass();


	@Autowired
	private  ResourceProperties resourceProperties;

	@Autowired
	private UserOperateService operateService;



	private UserOperate userOperate = new UserOperate();

	public UserOperate getUserOperate() {
		return userOperate;
	}

	public void setUserOperate(UserOperate userOperate) {
		this.userOperate = userOperate;
	}

	@Autowired
	public UserOperateDao userOperateDao;

	/***
	 * 用户重置密码
	 * **/
	@RequestMapping(value = "/resetPwdForUser", method = { RequestMethod.POST })
	@ResponseBody
	public JSONResult restPwdForUser(HttpServletRequest request) {
		JSONResult jsonResult = new JSONResult();
		try {
			String userPhoneNumb = request.getParameter("userPhoneNumb");// 手机号码
			String code = request.getParameter("code"); // 验证码
			String newPwd = request.getParameter("newPwd");// 新密码

			String sercode = (String) request.getSession().getAttribute("code");// 服务端验证码
		
			
			/*** 手机号码和验证码 校验 */
			if ("".equals(userPhoneNumb) || userPhoneNumb == null) {
				jsonResult.setStatus(ERRORSTATUS);
				jsonResult.setDesc( "手机号码为空");
				return jsonResult;
			}
			if(StringUtils.isBlank(newPwd)){
				jsonResult.setStatus(ERRORSTATUS);
				jsonResult.setDesc( "没有填写新密码");
				return jsonResult;
			}
			String codeTime = (String) request.getSession()
					.getAttribute("codeTime");
			log.info("userPhoneNumb=" + userPhoneNumb + ",code=" + code
					+ ",newPwd=" + newPwd + ",sercode=" + sercode
					+ ",codeTime=" + codeTime);

			String ckCode = ckCode(code, codeTime , request);// 检查验证码是否有效 和是否过期。

			if ("0".equals(ckCode)) {
				jsonResult.setStatus(ERRORSTATUS);
				jsonResult.setDesc( "验证码错误");
				return jsonResult;
			}
			if (UserStatusEnum.USER_REGITCODE_EXPIRED.getCode().equals(ckCode)) {
				jsonResult.setStatus(UserStatusEnum.USER_REGITCODE_EXPIRED.getCode());
				jsonResult.setDesc( UserStatusEnum.USER_REGITCODE_EXPIRED.getDescription());
				return jsonResult;
			}

			int count = operateService.updateUserPwd(userPhoneNumb,
					MD5.getMD5Passwored(newPwd));
			log.info("count=" + count);

			
			jsonResult.setStatus(UserStatusEnum.USER_UPDATEPWD_SUC.getCode());
			jsonResult.setDesc( UserStatusEnum.USER_UPDATEPWD_SUC.getDescription());
			
		} catch (Exception e) {
			log.info(e.getMessage());
			jsonResult.setStatus(ERRORSTATUS);
			jsonResult.setDesc( "系统异常");
		}
		return jsonResult;
	}

	// 使用用户名和密码登录

	@RequestMapping(value = "/login", method = { RequestMethod.POST})
	@ResponseBody
	public JSONResult login(HttpServletRequest request) throws IOException {
	     log.info("............start to login....................");
		// 获取登录用户名和密码和相关信息
		String userPhoneNumb = request.getParameter("userPhoneNumb");
		String password = request.getParameter("userPassword");

		if (userPhoneNumb == "") {
			userPhoneNumb = null;
		}
		if (password == "") {
			password = null;
		}
		JSONResult jsonResult = new JSONResult();
		UserOperate user = new UserOperate();
		user.setUserPhoneNumb(userPhoneNumb);
		// 判断用户是否存在 根据手机号去判断查询 （手机号唯一）
		String listResult = operateService.findUsers(user);
		// sb.append("[");
		if (UserStatusEnum.USER_EXISTED.getCode().equals(listResult)) {
			user.setUserPassword(password);// 用户名密码是否正确
			String haveStr = operateService.login(user);
			if (UserStatusEnum.USER_LOGIN_SUC.getCode().equals(haveStr)) {
				// 根据用户登录信息查询用户信息集合
				// ，修改用户信息-----------------------------------------strat
				List<UserOperate> userUpdate = operateService
						.findRegistInfoBackClien(user);
				String updateRsult = null;
				for (UserOperate userId : userUpdate) {// 得到用户对象
					updateRsult = operateService.updateLoginToken(userId);// 调用方法更新登录信息（令牌、有效期）
				}
				if (updateRsult.equals(UserStatusEnum.USER_UPDATETOKEN_SUC
						.getCode())) {
					List<UserOperate> userOp = null;
					userOp = operateService.findRegistInfoBackClien(user);// 登录成功后根据登录用户查询用户的token令牌等信息返给客户端登录
					for (UserOperate userInfo : userOp) {
						userInfo.setUserPassword("");
						userInfo.setFigureurl(resourceProperties.getFileHttpUrl()+userInfo.getFigureurl());
						if(log.isDebugEnabled()){
							log.debug("login user :"+userInfo);
						}
						jsonResult.setData(userInfo);

					}
				}
				jsonResult.setStatus(UserStatusEnum.USER_LOGIN_SUC.getCode());
				jsonResult.setDesc(UserStatusEnum.USER_LOGIN_SUC.getDescription());

			} else {
				log.info("密码错误");
				jsonResult.setStatus(haveStr);
				jsonResult.setDesc(UserStatusEnum.USER_LOGIN_PWD_ERROR.getDescription());


			}
		} else {
			log.info("用户名不存在");
			jsonResult.setStatus(listResult);
			jsonResult.setDesc(UserStatusEnum.USER_LOGIN_USENAME_ERROR.getDescription());

			// this.getResponse().sendRedirect(request.getContextPath()+"/login.jsp");
		}
		// sb.append("]");
		log.info("检查用户返回json=" + jsonResult.toString());

		return jsonResult;
	}

	// 用户注册，并返回注册后信息
	@RequestMapping(value = "/regist", method = { RequestMethod.POST})
	@ResponseBody
	public JSONResult regist( HttpServletRequest request ) throws IOException, ParseException {
		// 获取登录用户名和密码
		// String userName = request.getParameter("userName");
		String userPhoneNumb = request.getParameter("userPhoneNumb");
		String password = request.getParameter("userPassword");
		
		String userSrc = request.getParameter("userSrc");
		// String Ppassword = request.getParameter("PuserPassword");
		// 获取用户输入的验证码
		String inputCode = request.getParameter("checkCode");
		// 验证码有效时间
		log.info("user regist @sessionid==:"
				+ request.getSession().getId());
		String codeTime = (String) request.getSession()
				.getAttribute("codeTime");
		JSONResult jsonResult = new JSONResult();
		if (StringUtils.isBlank(userPhoneNumb) || StringUtils.isBlank(password)
				|| StringUtils.isBlank(inputCode)) {
			// this.resultMap.put("msg", "003");
			jsonResult.setStatus(UserStatusEnum.USER_REGIT_FAIL.getCode());
			return jsonResult;
		}

		if (codeTime != null) {// 验证码过期时间
			String ckCode = ckCode(inputCode, codeTime, request);// 检查验证码是否有效
			if (ckCode != UserStatusEnum.USER_REGITCODE_SUC.getCode()) {
				jsonResult.setStatus(ckCode);
				jsonResult.setDesc(UserStatusEnum.USER_REGITCODE_FAIL.getDescription());
			} else {
				try {
					UserOperate user = prepareUserInfo(userPhoneNumb, password);
					user.setUserType(userSrc);
					log.info("user info @@" + user);
					String r = operateService.registAccount(user);
					if (r.equals(UserStatusEnum.USER_REGIT_FAIL.getCode())) {// 注册失败
						jsonResult.setStatus(UserStatusEnum.USER_REGIT_FAIL
								.getCode());
						jsonResult.setDesc(UserStatusEnum.USER_REGIT_FAIL
								.getDescription());
						//
					} else if (r.equals(UserStatusEnum.USER_EXISTED.getCode())) {
						jsonResult
								.setStatus(UserStatusEnum.USER_EXISTED.getCode());
						jsonResult.setDesc(UserStatusEnum.USER_EXISTED
								.getDescription());
					} else if (r.equals(UserStatusEnum.USER_REGIT_SUC.getCode())) {// 注册成功
						log.info("ok注册成功");
						// 根据用户注册信息返回用户注册相关信息******(返回信息)
						jsonResult.setStatus(UserStatusEnum.USER_REGIT_SUC.getCode());
						jsonResult.setDesc(UserStatusEnum.USER_REGIT_SUC.getDescription());
						jsonResult.setData(user);

					}

				} catch (Exception e) {
					e.printStackTrace();
					jsonResult.setStatus(UserStatusEnum.USER_REGIT_EXCEPTION
							.getCode());
					jsonResult.setDesc(UserStatusEnum.USER_REGIT_EXCEPTION
							.getDescription());
					return jsonResult;
				}
			}
		} else {
			jsonResult.setStatus(UserStatusEnum.USER_REGITCODE_ERROR.getCode());
			jsonResult.setDesc(UserStatusEnum.USER_REGITCODE_ERROR.getDescription());
		}
		return jsonResult;
	}

	private UserOperate prepareUserInfo(String userPhoneNumb, String password) {
		UserOperate user = new UserOperate();
		user.setUserPhoneNumb(userPhoneNumb);
		user.setUserPassword(password);
		// 用户访问令牌
		String token = TokenProcessor.getInstance().generateToken("lingpai",
				true);
		user.setAccessToken(token);
		user.setExpireseIn(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		// 唯一标识
		UUID uuid = UUID.randomUUID();
		String uid = String.valueOf(uuid);
		user.setOpenId(String.valueOf(uuid));
		// 验证唯一标识的秘钥
		String openidKey = TokenProcessor.getInstance()
				.generateToken(uid, true);
		user.setOpenKey(openidKey);
		// 调用方法，查询phicommId，然后加1
		List<UserOperate> findUserPhicommList = userOperateDao
				.findEndPhicommId();
		int phicommId = 100000;
		try {
			if (findUserPhicommList.size() > 0) {
				for (UserOperate userOperateObj : findUserPhicommList) {
					
					
					
					phicommId = Integer.valueOf(userOperateObj.getPhicommId())
							.intValue() + 1;// 将String转换成int型数据
					break;
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user.setPhicommId(String.valueOf(phicommId));
		user.setUserPassword(MD5.getMD5Passwored(user.getUserPassword()));// 使用md5对密码加密
		return user;
	}

	// 验证用户是否存在(返回：001存在；000不存在)
	@RequestMapping(value = "/ckUserExt", method = { RequestMethod.POST})
	@ResponseBody
	public String ckUserExt(HttpServletRequest request,HttpServletResponse response) throws IOException {

		// 获取登录用户名和密码
		String userPhoneNumb = request.getParameter("userPhoneNumb");
		// String userEmail = request.getParameter("userEmail");
		// String userPhoneNumb =
		// request.getParameter("userPhoneNumb");
		if (userPhoneNumb == "") {
			userPhoneNumb = null;
		}
		UserOperate user = new UserOperate();
		user.setUserName(userPhoneNumb);
		// 调用业务，判断用户是否存在
		String listResult = operateService.findUsers(user);// 返回000说明用户不存在、001说明用户已存在
		if (null != listResult) {
			StringBuffer sb = new StringBuffer();
			// sb.append("[");
			// 得到集合的长度
			sb.append("{\"msg\":\""); // 检查用户返回结果
			sb.append(listResult);
			sb.append("\"}");
			// sb.append(",");
			// sb.append("]");
			System.out.println("检查用户返回json=" + sb);
			PrintWriter out = response.getWriter();
			out.print(sb.toString());
			out.flush();
			out.close();
			return null;
		} else {
			return "{}";
		}
	}

	// 根据用户端保存的tokenId和openId登录----------是否有效并返回用户基本信息-------------------------------------------------------------
	@RequestMapping(value = "/getSingleUser", method = { RequestMethod.POST})
	@ResponseBody
	public JSONResult getSingleUser(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		// 用户名密码
	     log.info("............start to getSingleUser....................");
	     log.info("............start to getSingleUser....................");
		// 令牌和过期时间
		String tokenId = request.getParameter("tokenId");// 令牌
		String openId = request.getParameter("openId");// openId
		String expireseIn = request.getParameter("expireseIn");// 得到用户上次登录的时间
		
		JSONResult jsonResult = new JSONResult();
		Map<String,String> resultMap = new HashMap<String,String>();
		// 判断天数是否已经过期
		StringBuffer sb = new StringBuffer();
		try {
			// tokendate="2014-12-5 12:12:12";
			userOperate.setAccessToken(tokenId);
			userOperate.setOpenId(openId);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date1 = df.parse(expireseIn);
			Date date2 = new Date();// 得到用户当前登录时间
			Long dif = date2.getTime() - date1.getTime();// 得到两者之间的时间差
			System.out.println(dif);
			Long day = dif / (1000 * 60 * 60 * 24);// 转换成天

			// sb.append("[");
			if (day >= 90) {
				System.out.println("用户已过期");
				
				jsonResult.setStatus(ERRORSTATUS);
				jsonResult.setDesc("用户已过期");
			} else {

				// 调用业务，判断用户是否存在
				List<UserOperate> listResult = operateService
						.getSempUser(userOperate);
				if (listResult.size() > 0) {
					jsonResult.setStatus("001");
					jsonResult.setDesc("用户登陆有效。");
					// sb.append(",");

					for (UserOperate uo : listResult) {// 分装用户信息返回
						jsonResult.setData(uo);
					}
					
				} else {
					jsonResult.setStatus(ERRORSTATUS);
					jsonResult.setDesc("用户登录失败");
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			jsonResult.setStatus(ERRORSTATUS);
			jsonResult.setDesc("用户登录失败");
		}

		
		return jsonResult;
	}

	// 验证码验证分装方法=============（分装方法）

	public String ckCode(String inputCode, String codeTime,HttpServletRequest request) throws IOException,
			ParseException {
		String result = "0";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date2 = df.parse(codeTime);
		// 获取当前时间
		String dat1 = df.format(new Date());
		Date date1 = df.parse(dat1);
		Long dif = date1.getTime() - date2.getTime();// 得到两者之间的时间差
		Long day = dif / (1000);// 转换成秒
		// 获取短信验证码 session中的
		String code = (String) request.getSession()
				.getAttribute("code");
		if (code.equals(inputCode)) {
			Long validatecode_time = Long.valueOf(resourceProperties.getCodetime());
			if (code == "" || day >validatecode_time ){// 判断是否已失效 60秒失效
				result = UserStatusEnum.USER_REGITCODE_EXPIRED.getCode();// 失败
				log.info("验证码过期");
			} else {
				result = UserStatusEnum.USER_REGITCODE_SUC.getCode();// 成功
			}
		} else {
			result = UserStatusEnum.USER_REGITCODE_ERROR.getCode();// 失败，验证码错误
		}
		return result;
	}

	// 退出，注销用户
	@RequestMapping(value = "/logout", method = { RequestMethod.POST})
	@ResponseBody
	public JSONResult logout(HttpServletRequest request) throws IOException {
		String tokenId = request.getParameter("tokenId");// 令牌
		String openId = request.getParameter("openId");// openId
		String expireseIn = request.getParameter("expireseIn");// 得到用户上次登录的时间
		UserOperate userOperate = new UserOperate();
		userOperate.setAccessToken(tokenId);
		userOperate.setOpenId(openId);
		userOperate.setExpireseIn(expireseIn);
		String updateRsult = null;
		List<UserOperate> listResult = operateService.getSempUser(userOperate);// 根据用户tokenId和openId查询用户
		for (UserOperate userId : listResult) {// 得到用户对象
			updateRsult = operateService.updateLoginToken(userId);// 调用方法更新登录信息（令牌、有效期）
		}
		JSONResult jsonResult = new JSONResult();
		StringBuffer sb = new StringBuffer();
		// sb.append("[");
		if (updateRsult != null && updateRsult != "") {
			if (updateRsult.equals("007")) {
				jsonResult.setStatus("009");
//				sb.append("{\"msg\":\""); // 检查用户返回结果 ，注销成功
//				sb.append("009\"");
//				// sb.append(",");
//				sb.append("}");
				jsonResult.setDesc("注销成功");
			}
		} else {
			jsonResult.setStatus("005");
//			sb.append("{\"msg\":\""); // 检查用户返回结果 ，异常
//			sb.append("005\"");
//			// sb.append(",");
//			sb.append("}");
		}
		// sb.append("]");
	
         return jsonResult;
	}

	// 修改用户密码
	@RequestMapping(value = "/updatePassword", method = { RequestMethod.POST})
	@ResponseBody
	public JSONResult updatePassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String openid = request.getParameter("openId");
		String oldPassoword = request.getParameter("userPassword");
		String newPwd = request.getParameter("newPassword");
		JSONResult jsonResult = new JSONResult();
		if(StringUtils.isBlank(oldPassoword)){
	
			jsonResult.setStatus(UserStatusEnum.USER_UPDATEPWD_FAIL.getCode());
			jsonResult.setDesc(UserStatusEnum.USER_UPDATEPWD_FAIL.getDescription());
		}else{
			// 根据客户端传来的唯一id查找用户
			UserOperate userOperate = operateService.findUserByOpenId(openid);
			
			if (userOperate != null) {
				String password = userOperate.getUserPassword();
				if ( MD5.getMD5Passwored(oldPassoword).equals(password)) {
					// 调用修改方法
					int count = operateService.updateUserPwd(userOperate.getUserPhoneNumb(),
							MD5.getMD5Passwored(newPwd));
					jsonResult.setStatus(UserStatusEnum.USER_UPDATEPWD_SUC.getCode());
					jsonResult.setDesc(UserStatusEnum.USER_UPDATEPWD_SUC.getDescription());
				} else {
					jsonResult.setStatus(UserStatusEnum.USER_UPDATEPWD_WRONGPWD.getCode());
					jsonResult.setDesc(UserStatusEnum.USER_UPDATEPWD_WRONGPWD.getDescription());
				}
			} else {
				jsonResult.setStatus(UserStatusEnum.USER_UPDATEPWD_FAIL.getCode());
				jsonResult.setDesc(UserStatusEnum.USER_UPDATEPWD_FAIL.getDescription());
			}
		}
	
		   return jsonResult;
	}

	//用户上传头像
	// 修改用户密码
	@RequestMapping(value = "/uploadHeadPortrait", method = { RequestMethod.POST})
	@ResponseBody
	public JSONResult uploadHeadPortrait(HttpServletRequest request) throws FileUploadException, IOException{
		
		String openid = request.getParameter("openId");
		String headPortrait = request.getParameter("headPortrait");
		System.out.println("openId-----------------"+openid+"headPortrait----------------------"+headPortrait);
		JSONResult jsonResult = new JSONResult();
	    Map<String,String> resultMap = new HashMap<String,String>();
		 //对字节数组字符串进行Base64解码并生成图片  
        if (headPortrait == null){ //图像数据为空  
        	jsonResult.setStatus(ERRORSTATUS);
        	jsonResult.setDesc("图像数据为空  ");
            return jsonResult;
        }
        BASE64Decoder decoder = new BASE64Decoder();  
        try   
        {  
            //Base64解码  
            byte[] b = decoder.decodeBuffer(headPortrait);  
            for(int i=0;i<b.length;++i)  
            {  
                if(b[i]<0)  
                {//调整异常数据  
                    b[i]+=256;  
                }  
            }  
            String http="";
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            String datedir =df.format(new Date());  
            String randomdir = RandomStringUtils.randomAlphanumeric(10) ;
            String uploadPath = "/upload/headPortrait/"+datedir+"/"+randomdir+"/" ;
//            String uploadPath ="/upload/";
            //生成jpg图片  
            String name = openid+".jpg";//新生成的图片  
            IFileUploadRemote remote =(IFileUploadRemote)SpringContextUtil.getBean("fileRemote");
            http = remote.upload(b, uploadPath,name);
            log.info(http+"=======");
	            //根据用户唯一id修改用户头像信息
	            operateService.updateUserHeadPortraitByOpenid(openid, uploadPath+name);//修改的方法
	            jsonResult.setStatus( "001");
	            jsonResult.setDesc("上传成功");
	            
	            int index = http.indexOf("/upload");
                String str_temp = new String();
                str_temp = http.substring(index,http.length());
                http=resourceProperties.getFileHttpUrl()+str_temp;
               
                log.info(http);
                resultMap.put("headPortraitUrl", http);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.setStatus( ERRORSTATUS);
	        jsonResult.setDesc("系统异常");
		
		}
        jsonResult.setData(resultMap);
		return jsonResult;
	}

	// 根据用户ID查询用户信息展示
	@SuppressWarnings("unchecked")
	public String findUserInfoByOpenId(HttpServletRequest request) {

		String openId = request.getParameter("openId");
		try {
			UserOperate userOperate = operateService.findUserByOpenId(openId);
			if (userOperate != null) {
				/*
				 * resultMap.put("userName", userOperate.getUserName());
				 * resultMap.put("phicommId", userOperate.getPhicommId());
				 * resultMap.put("userPhoneNumb",
				 * userOperate.getUserPhoneNumb()); resultMap.put("nickname",
				 * userOperate.getNickname()); resultMap.put("sex",
				 * userOperate.getSex()); resultMap.put("birthday",
				 * userOperate.getBirthday()); resultMap.put("province",
				 * userOperate.getProvince()); resultMap.put("city",
				 * userOperate.getCity()); this.resultMap.put("status", 1);
				 * this.resultMap.put("msg", "查询成功");
				 * this.resultMap.put("success", true);
				 */

				request.setAttribute("userInfo", userOperate);
			}
		} catch (Exception e) {
			this.resultMap.put("status", 0);
			this.resultMap.put("msg", "数据异常");
		}
		return "wap/userInfo";
	}



	public String toEditUserInfo(HttpServletRequest request) {
		String openId = request.getParameter("openId");
		UserOperate userOperate = operateService.findUserByOpenId(openId);
		request.setAttribute("userInfo", userOperate);
		return "wap/demo";
	}

	

}
