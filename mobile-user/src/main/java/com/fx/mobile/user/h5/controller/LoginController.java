package com.fx.mobile.user.h5.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.fx.mobile.bean.JSONResult;
import com.fx.mobile.model.UserOperate;
import com.fx.mobile.user.action.BaseAction;
import com.fx.mobile.user.constants.enums.UserStatusEnum;
import com.fx.mobile.user.dao.UserOperateDao;
import com.fx.mobile.user.service.ResourceProperties;
import com.fx.mobile.user.service.UserOperateService;
import com.fx.mobile.user.util.InterfaceClass;
import com.fx.mobile.user.util.MD5;
import com.fx.mobile.user.util.MailActivationCodeProcessor;
import com.fx.mobile.user.util.TokenProcessor;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;

@Controller
@RequestMapping(value = "/h5/useroperate")
public class LoginController extends BaseAction{
	
	private static final String ERRORSTATUS = "000";


	InterfaceClass interfaceClass = new InterfaceClass();


	@Autowired
	public  ResourceProperties resourceProperties;

	@Autowired
	private UserOperateService operateService;
	@Autowired
	public UserOperateDao userOperateDao;

	 public Log log = LogFactory.getLog(getClass()); 
	/**
	 * 注册首页
	 */
	@RequestMapping("/toregister")
	public ModelAndView toRegister(HttpServletResponse response) {
		ModelAndView mav  = new ModelAndView("/muser/register_body");
		return mav;
	}
	
	/**
	 * 注册首页
	 */
	@RequestMapping("/tologin")
	public ModelAndView toLogin(HttpServletResponse response) {
		ModelAndView mav  = new ModelAndView("/muser/login_body");
		return mav;
	}
	
	/**
	 * edit首页
	 * @param user
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/toedit")
	public ModelAndView toEdit(HttpServletResponse response) {
		ModelAndView mav  = new ModelAndView("/muser/edit_body");
		return mav;
	}
	/**
	 * 是否是三方账号登录的我的斐讯
	 */
	@RequestMapping(value = "/isthreelogin", method = { RequestMethod.POST})
	@ResponseBody
	public JSONResult isThreeLogin(HttpServletRequest request)  throws IOException{
		JSONResult jsonResult=new JSONResult();
		String phicommId=request.getParameter("phicommId");
		UserOperate user=userOperateDao.findUserByPhicommId(phicommId).get(0);
		if(user.getOpenqq().isEmpty() && user.getOpenweixin().isEmpty()){
			if(user.getUserPhoneNumb().isEmpty()){
				jsonResult.setStatus("000");
				jsonResult.setDesc("不是");
			}
		}
		return jsonResult;
	}
	/**
	 * qq解绑处理
	 *
	 */
		@RequestMapping(value = "/releaseqqbind", method = { RequestMethod.POST})
		@ResponseBody
		public JSONResult releaseQQBind(HttpServletRequest request)  throws IOException{
			JSONResult jsonResult=new JSONResult();
			String phicommId=request.getParameter("phicommId");
			UserOperate user=userOperateDao.findUserByPhicommId(phicommId).get(0);
			user.setOpenqq(null);
			userOperateDao.update(user);
		    jsonResult.setStatus("001");
			jsonResult.setDesc("解除绑定成功");
			return jsonResult;
		}
	
	/**
	 * 找回密码处理
	 */
	@RequestMapping(value = "/resetpass", method = { RequestMethod.POST})
	@ResponseBody
	public JSONResult resetPass(HttpServletRequest request)  throws IOException{
		JSONResult jsonResult = new JSONResult();
		UserOperate user=new UserOperate();
		String pass=request.getParameter("pass");
		pass=MD5.getMD5Passwored(pass);
		String phicommId=request.getParameter("phicommId");
		user=userOperateDao.findUserByPhicommId(phicommId).get(0);
		user.setUserPassword(pass);
		userOperateDao.update(user);
		jsonResult.setDesc("修改密码成功");
		jsonResult.setStatus("001");
		return jsonResult;
	}
	/**
	 * 修改密码处理
	 */
	@RequestMapping(value = "/changepass", method = { RequestMethod.POST})
	@ResponseBody
	public JSONResult changePass(HttpServletRequest request)  throws IOException{
		JSONResult jsonResult = new JSONResult();
		UserOperate user=new UserOperate();
		String pass=request.getParameter("pass");
		String passold=request.getParameter("passold");
		passold=MD5.getMD5Passwored(passold);
		pass=MD5.getMD5Passwored(pass);
		String phicommId=request.getParameter("phicommId");
		user=userOperateDao.findUserByPhicommId(phicommId).get(0);
		if(passold.equals(user.getUserPassword())){
			user.setUserPassword(pass);
			userOperateDao.update(user);
			jsonResult.setDesc("修改密码成功");
			jsonResult.setStatus("001");
		}else{
			jsonResult.setDesc("旧密码输入错误，请输入正确密码");
			jsonResult.setStatus("000");
		}
		return jsonResult;
	}
	/**
	 *根据key更新数据
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/updatebykey", method = { RequestMethod.POST})
	@ResponseBody
	public JSONResult updatebykey(HttpServletRequest request)  throws IOException{
		  log.info("............start to updatebykeycontroller....................");
		JSONResult jsonResult = new JSONResult();
		String key=request.getParameter("key");
		String value=request.getParameter("value");
		String phicomm_id=request.getParameter("phicomm_id");
		if(value.equals("") || value==null){
			jsonResult.setStatus(UserStatusEnum.USER_UPDATEBYKEY_NULL.getCode());
			jsonResult.setDesc(UserStatusEnum.USER_UPDATEBYKEY_NULL.getDescription());
			return jsonResult;
		}
		if(operateService.updateUserOperateByKey(key, value,phicomm_id)==true){
			jsonResult.setStatus(UserStatusEnum.USER_UPDATEBYKEY_SUC.getCode());
			jsonResult.setDesc(UserStatusEnum.USER_UPDATEBYKEY_SUC.getDescription());
			if(key.equals("nickname")){
			    jsonResult.setDesc("昵称设置成功");
			}
			if(key.equals("sex")){
				jsonResult.setDesc("性别设置成功");
			}
			if(key.equals("birthday")){
				jsonResult.setDesc("生日设置成功");
			}
		}else{
			jsonResult.setStatus(UserStatusEnum.USER_UPDATEBYKEY_FAIL.getCode());
			jsonResult.setDesc(UserStatusEnum.USER_UPDATEBYKEY_FAIL.getDescription());
		}
		return jsonResult;
	}
	/**
	 *根据两个key更新数据
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/updatebykeytwo", method = { RequestMethod.POST})
	@ResponseBody
	public JSONResult updatebykeytwo(HttpServletRequest request)  throws IOException{
		  log.info("............start to updatebykeytwocontroller....................");
		JSONResult jsonResult = new JSONResult();
		String key1=request.getParameter("key1");
		String value1=request.getParameter("value1");
		String key2=request.getParameter("key2");
		String value2=request.getParameter("value2");
		String phicomm_id=request.getParameter("phicomm_id");
		if(value1.equals("") || value1==null || value2.equals("") || value2==null){
			jsonResult.setStatus(UserStatusEnum.USER_UPDATEBYKEY_NULL.getCode());
			jsonResult.setDesc(UserStatusEnum.USER_UPDATEBYKEY_NULL.getDescription());
			return jsonResult;
		}
		if(operateService.updateUserOperateByKey2(key1, value1,key2,value2,phicomm_id)==true){
			jsonResult.setStatus(UserStatusEnum.USER_UPDATEBYKEY_SUC.getCode());
			jsonResult.setDesc(UserStatusEnum.USER_UPDATEBYKEY_SUC.getDescription());
		}else{
			jsonResult.setStatus(UserStatusEnum.USER_UPDATEBYKEY_FAIL.getCode());
			jsonResult.setDesc(UserStatusEnum.USER_UPDATEBYKEY_FAIL.getDescription());
		}
		return jsonResult;
	}
	 
	/**
	 * 找回密码（手机）
	 */
	@RequestMapping(value = "/findpassmobile", method = { RequestMethod.POST})
	@ResponseBody
	public JSONResult findPassMobile(HttpServletRequest request){
		JSONResult jsonResult=new JSONResult();
		// 验证码有效时间
		String codeTime = (String) request.getSession()
				.getAttribute("codeTime");
		String inputCode=request.getParameter("inputCode");
		try {
			
			String ckCode = ckCode(inputCode, codeTime, request);
			if(ckCode.equals(UserStatusEnum.USER_REGITCODE_SUC.getCode())){
				jsonResult.setStatus("001");
				jsonResult.setDesc("验证码验证通过");
			}else if(ckCode.equals(UserStatusEnum.USER_REGITCODE_EXPIRED.getCode())){
				jsonResult.setStatus("002");
				jsonResult.setDesc("验证码过期");
			}else{
				jsonResult.setStatus("000");
				jsonResult.setDesc("验证码验证错误");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 检查验证码是否有效
		return jsonResult;
	}

	/**
	 * 找回密码（邮箱）
	 */
	@RequestMapping(value = "/findpass", method = { RequestMethod.POST})
	@ResponseBody
	public JSONResult findPass(HttpServletRequest request) throws IOException {
		JSONResult jsonResult=new JSONResult();
		UserOperate user=new UserOperate();
		String userName=request.getParameter("userName");
		user.setUserName(userName);
		if(userOperateDao.isExistUserByUserName(user)){
			UserOperate userFinded=userOperateDao.findUsersByUserName(user).get(0);
			if(userFinded.getUserEmail()==null || userFinded.getUserEmail().equals("")){
				jsonResult.setStatus(UserStatusEnum.USERMAIL_NOT_EXISTED .getCode());
				jsonResult.setDesc(UserStatusEnum.USERMAIL_NOT_EXISTED .getDescription());
			}else{
				//产生重置码和时效
				user=userFinded;
				String findPassCode=MailActivationCodeProcessor.getInstance().generateToken("mailcode", true);
				user.setFindPassCode(findPassCode);
				user.setFindPassCodeExpireseIn(new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").format(new Date()));
				userOperateDao.update(user);
				//发送邮件重置
				if(operateService.resetMail(userFinded)){
					jsonResult.setStatus(UserStatusEnum.RESTMAIL_SUC .getCode());
					jsonResult.setDesc(UserStatusEnum.RESTMAIL_SUC .getDescription());
				}else{
					jsonResult.setStatus(UserStatusEnum.RESTMAIL_FAIL .getCode());
					jsonResult.setDesc(UserStatusEnum.RESTMAIL_FAIL .getDescription());
				}
			}
				 
		}else{
			jsonResult.setStatus(UserStatusEnum.USER_NOT_EXISTED.getCode());
			jsonResult.setDesc(UserStatusEnum.USER_NOT_EXISTED.getDescription());
		}
		return jsonResult;
	}
	/**
	 * 使用用户名和密码登录
	 * @param user
	 * @return
	 * @throws IOException
	 */

	@RequestMapping(value = "/login", method = { RequestMethod.POST})
	@ResponseBody
	public JSONResult login(@RequestBody UserOperate user) throws IOException {
	     log.info("............start to login....................");
		// 获取登录用户名和密码和相关信息
	     JSONResult jsonResult = new JSONResult();
         if(StringUtils.isBlank(user.getUserPhoneNumb() )){
 			jsonResult.setStatus(UserStatusEnum.USER_LOGIN_USER_ERROR.getCode());
 			jsonResult.setDesc(UserStatusEnum.USER_LOGIN_USER_ERROR.getDescription());
 			return jsonResult;
         }
		// 判断用户是否存在 根据手机号去判断查询 （手机号唯一）
		String listResult = operateService.findUsers(user);
		if (UserStatusEnum.USER_EXISTED.getCode().equals(listResult)) {
			 if(StringUtils.isBlank(user.getUserPassword())){
	        	jsonResult.setStatus(UserStatusEnum.USER_LOGIN_PASS_ERROR.getCode());
	  			jsonResult.setDesc(UserStatusEnum.USER_LOGIN_PASS_ERROR.getDescription());
	  			return jsonResult;
	         }
			if(operateService.isMailActivated(user.getUserPhoneNumb())){
				String haveStr = operateService.login(user);
				if (UserStatusEnum.USER_LOGIN_SUC.getCode().equals(haveStr)) {//这个if代表通过用户名和密码的验证
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
							//userInfo.setFigureurl(resourceProperties.getTestUrl()+userInfo.getFigureurl());
							if(log.isDebugEnabled()){
								log.debug("login user :"+userInfo);
							}
							jsonResult.setData(userInfo);

						}
					}
					jsonResult.setStatus(UserStatusEnum.USER_LOGIN_SUC.getCode());
					jsonResult.setDesc(UserStatusEnum.USER_LOGIN_SUC.getDescription());

				} else {
					jsonResult.setStatus(UserStatusEnum.USER_LOGIN_PASS_ERROR.getCode());
		  			jsonResult.setDesc(UserStatusEnum.USER_LOGIN_PASS_ERROR.getDescription());
		  			return jsonResult;
				}
			}else{
				jsonResult.setStatus(UserStatusEnum.USER_ACTIVATION_FAIL.getCode());
				jsonResult.setDesc(UserStatusEnum.USER_ACTIVATION_FAIL.getDescription());
			}
			
		} else {
			jsonResult.setStatus(UserStatusEnum.USER_LOGIN_USER_ERROR.getCode());
 			jsonResult.setDesc(UserStatusEnum.USER_LOGIN_USER_ERROR.getDescription());

			// this.getResponse().sendRedirect(request.getContextPath()+"/login.jsp");
		}
		// sb.append("]");
		log.info("检查用户返回json=" + jsonResult.toString());
		return jsonResult;
	}


	/**
	 * 注册处理
	 */
	@RequestMapping(value = "/registphone", method = { RequestMethod.POST})
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
	String codeTime = (String) request.getSession()
			.getAttribute("codeTime");
	JSONResult jsonResult = new JSONResult();
	/**
	 * 判断密码和用户名是否为空
	 */
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

//----------------------------------------------------------------------------------------------------	
	/**
	 * 邮箱注册
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping(value = "/registmail", method = { RequestMethod.POST})
	@ResponseBody
	public JSONResult registMail( HttpServletRequest request ) throws IOException, ParseException {
		String mail=request.getParameter("mail");
		String pwd=request.getParameter("pwd");
		String username=request.getParameter("username");
		UserOperate user = prepareUserInfoMail(username, mail,pwd);
		String code=operateService.mailRegister(user);
		JSONResult jsonResult = new JSONResult();
		if(code.equals(UserStatusEnum.USER_REGISTERMAIL_USERNAMEEXIT.getCode())){
			jsonResult.setStatus(UserStatusEnum.USER_REGISTERMAIL_USERNAMEEXIT.getCode());
			jsonResult.setDesc(UserStatusEnum.USER_REGISTERMAIL_USERNAMEEXIT.getDescription());
		}
		if(code.equals(UserStatusEnum.USER_REGISTERMAIL_USERMAILEXIT.getCode())){
			jsonResult.setStatus(UserStatusEnum.USER_REGISTERMAIL_USERMAILEXIT.getCode());
			jsonResult.setDesc(UserStatusEnum.USER_REGISTERMAIL_USERMAILEXIT.getDescription());
		}
		if(code.equals(UserStatusEnum.USER_REGISTERMAIL_FAIL.getCode())){
			jsonResult.setStatus(UserStatusEnum.USER_REGISTERMAIL_FAIL.getCode());
			jsonResult.setDesc(UserStatusEnum.USER_REGISTERMAIL_FAIL.getDescription());
		}
		if(code.equals(UserStatusEnum.USER_SENDMAIL_FAIL.getCode())){
			jsonResult.setStatus(UserStatusEnum.USER_SENDMAIL_FAIL.getCode());
			jsonResult.setDesc(UserStatusEnum.USER_SENDMAIL_FAIL.getDescription());
		}
		if(code.equals(UserStatusEnum.USER_SENDMAIL_SUC.getCode())){
			jsonResult.setStatus(UserStatusEnum.USER_SENDMAIL_SUC.getCode());
			jsonResult.setDesc(UserStatusEnum.USER_SENDMAIL_SUC.getDescription());
		}
		
		return jsonResult;
	}
//---------------------------------------------------------------------------------------------------
	
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
			System.out.print("code======================="+code +""  + inputCode) ;
			if (code.equals(inputCode)) {
				Long validatecode_time = Long.valueOf(resourceProperties.getCodetime());
				System.out.println("day============================"+day+"validatecodetime============"+validatecode_time);
				if (StringUtils.isEmpty(code) || day >validatecode_time ){// 判断是否已失效 60秒失效
					result = UserStatusEnum.USER_REGITCODE_EXPIRED.getCode();// 失败
					log.info("验证码过期");
				} else {
					result = UserStatusEnum.USER_REGITCODE_SUC.getCode();// 成功
				}
			} else {
				result = UserStatusEnum.USER_REGITCODE_ERROR.getCode();// 失败，验证码错误
			}
			
			System.out.println("result ====" + result);
			return result;
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
		
		private UserOperate prepareUserInfoMail(String userName,String mail, String password) {
			UserOperate user = new UserOperate();
			user.setUserName(userName);
			user.setUserEmail(mail);
			user.setUserPassword(password);
			String mailActivationCode=MailActivationCodeProcessor.getInstance().generateToken("mailcode", true);
			user.setMailActivationCode(mailActivationCode);
			user.setMailActivationCodeExpireseIn(new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").format(new Date()));
			// 唯一标识
			UUID uuid = UUID.randomUUID();
			String uid = String.valueOf(uuid);
			user.setOpenId(String.valueOf(uuid));
			// 验证唯一标识的秘钥
			String openidKey = MailActivationCodeProcessor.getInstance().generateToken(uid, true);
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
}
