package com.fx.mobile.user.h5.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fx.mobile.bean.JSONResult;
import com.fx.mobile.model.UserOperate;
import com.fx.mobile.model.WeiXinUserInfo;
import com.fx.mobile.user.action.BaseAction;
import com.fx.mobile.user.constants.enums.UserStatusEnum;
import com.fx.mobile.user.dao.UserOperateDao;
import com.fx.mobile.user.service.ResourceProperties;
import com.fx.mobile.user.service.UserOperateService;
import com.fx.mobile.user.util.MD5;
import com.fx.mobile.user.util.TokenProcessor;
import com.fx.mobile.weixin.connect.utils.WeixinConnectConfig;
import com.fx.mobile.weixin.connect.utils.http.HttpXmlClient;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;

@Controller
@RequestMapping(value = "/union")
public class UnifiedAccountNumberOtherLoginController extends BaseAction {

	@Autowired
	private UserOperateService operateService;

	@Autowired
	public  ResourceProperties resourceProperties;
	
    public Log log = LogFactory.getLog(getClass()); 
    
	@Autowired
	public UserOperateDao userOperateDao;
	// 退出，注销用户
	public void qqlogin() {
		//System.out.println("1231231231");
	//	log.debug("qqlogin");
	

	}
	@RequestMapping(value = "/toqqbind")
	public void toqqBind(HttpServletRequest request, HttpServletResponse response) {
		try {
			log.info("1231231231");
			String authorizeURL = new Oauth().getAuthorizeURL(request);
			String phicommId=request.getParameter("phicommId");
			request.getSession().setAttribute("phicommId", phicommId);
			log.info("authorizeURL===========================================================" + authorizeURL);
			request.getSession().setAttribute("state", "bind");
			//response.addIntHeader(name, value);
			response.sendRedirect(authorizeURL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@RequestMapping(value = "/toqqlogin")
	public void toqqlogin(HttpServletRequest request, HttpServletResponse response) {
		try {
			log.info("1231231231");
			String authorizeURL = new Oauth().getAuthorizeURL(request);
			log.info("authorizeURL===========================================================" + authorizeURL);
			request.getSession().setAttribute("state", "login");
			response.sendRedirect(authorizeURL);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * toweixinlogin
	 */
	@RequestMapping(value = "/toweixinlogin")
	public void toweixinlogin(HttpServletRequest request, HttpServletResponse response) {
		try {
			String authorizeURL=new String();
			String redirect_uri=WeixinConnectConfig.getValue("redirect_URI");
			String myredirect_uri = java.net.URLEncoder.encode(redirect_uri, "utf-8");
			log.info("myredirecturi===========================================================" + myredirect_uri);
			String appId=WeixinConnectConfig.getValue("AppID");
			String response_type=WeixinConnectConfig.getValue("responseType");
			String scope=WeixinConnectConfig.getValue("scope");
			String state="abcd";
			//System.out.println("authorizeURL====" + authorizeURL);
			authorizeURL="https://open.weixin.qq.com/connect/qrconnect?appid="+appId+"&redirect_uri="+myredirect_uri+"&response_type="+response_type+"&scope="+scope+"#wechat_redirect";
		//	String getAccessTokenByRequest
			response.sendRedirect(authorizeURL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	 /**
     * 微信原生登录
     */
	@RequestMapping(value="/wxlogin",method = { RequestMethod.POST})
	@ResponseBody
	public JSONResult toWxRegOrLogin(HttpServletRequest request,HttpServletResponse response){
		Enumeration<String> attr = request.getAttributeNames();
		JSONResult jsonResult=new JSONResult();
		String result = "";
		InputStream in;
		try {
			in = request.getInputStream();
			byte[] datas = new byte[1024];
			int len = 0;
			while((len = in.read(datas))!= -1){
				result +=new String(datas);
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		System.out.println("result:"+result);	
		try {
			JSONObject json= new JSONObject(result);
			WeiXinUserInfo weiXinUserInfo=new WeiXinUserInfo();
			weiXinUserInfo.setOpenid(json.getString("openid"));
			weiXinUserInfo.setNickname(json.getString("nickname"));
			weiXinUserInfo.setSex(json.getString("sex"));
			weiXinUserInfo.setLanguage(json.getString("language"));
			weiXinUserInfo.setCity(json.getString("city"));
			weiXinUserInfo.setProvince(json.getString("province"));
			weiXinUserInfo.setCountry(json.getString("country"));
			weiXinUserInfo.setHeadimgurl(json.getString("headimgurl"));
			weiXinUserInfo.setUnionid(json.getString("unionid"));
	        System.out.print("---------------------------------------kadm-=====================================================-------"+weiXinUserInfo.getCity());
			if(operateService.booleanWeixinLogin(weiXinUserInfo.getUnionid())==false){
				   //生成账号，插入数据
				UserOperate user=prepareUserInfoWeixinInsert(weiXinUserInfo);
				boolean is=userOperateDao.regist(user);
				if(is==true){
					List<UserOperate> userOp=userOperateDao.finduserByOpenweixin(weiXinUserInfo.getUnionid());
	        		for (UserOperate userInfo : userOp) {
						userInfo.setUserPassword("");
						jsonResult.setData(userInfo);
						if(log.isDebugEnabled()){
							log.debug("login user :"+userInfo);
						}
	        		}
				   
					jsonResult.setStatus(UserStatusEnum.USER_LOGIN_SUC.getCode());
					jsonResult.setDesc(UserStatusEnum.USER_LOGIN_SUC.getDescription());
				}else{
					jsonResult.setStatus(UserStatusEnum.USER_SANFANGLOGIN_FAI.getCode());
					jsonResult.setDesc(UserStatusEnum.USER_SANFANGLOGIN_FAI.getDescription());
				}
			}else{

				String updateRsult = null;
				List<UserOperate> userUpdate=userOperateDao.finduserByOpenweixin(weiXinUserInfo.getUnionid());
				//更新Token
				for (UserOperate userId : userUpdate) {// 得到用户对象
					updateRsult = operateService.updateLoginToken(userId);// 调用方法更新登录信息（令牌、有效期）
				}
			        	
				if (updateRsult.equals(UserStatusEnum.USER_UPDATETOKEN_SUC.getCode())) {  
			   		List<UserOperate> userOp=userOperateDao.finduserByOpenweixin(weiXinUserInfo.getUnionid());
	        		for (UserOperate userInfo : userOp) {
						userInfo.setUserPassword("");
						jsonResult.setData(userInfo);
						if(log.isDebugEnabled()){
							log.debug("login user :"+userInfo);
						}
	        		}
				   
					jsonResult.setStatus(UserStatusEnum.USER_LOGIN_SUC.getCode());
					jsonResult.setDesc(UserStatusEnum.USER_LOGIN_SUC.getDescription());
	        	}else{
	        		jsonResult.setStatus(UserStatusEnum.USER_SANFANGLOGIN_UPDATEFAIL.getCode());
					jsonResult.setDesc(UserStatusEnum.USER_SANFANGLOGIN_UPDATEFAIL.getDescription());
	        	}
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return jsonResult;
	}
	/**
	 * 微信H5登录
	 * @param request
	 * @param response
	 * @return
	 */
//	@SuppressWarnings("null")
//	@RequestMapping("/afterweixinlogin")
//	public ModelAndView toWeiXinLoginSuccess(HttpServletRequest request,HttpServletResponse response) {
//		JSONResult jsonResult = new JSONResult();
//		String tokenUrl=null;
//		String code=request.getParameter("code");
//		String accessTokenURL=WeixinConnectConfig.getValue("accessTokenURL");
//		String appid=WeixinConnectConfig.getValue("AppID");
//		String secret=WeixinConnectConfig.getValue("AppSecret");
//		String grant_type=WeixinConnectConfig.getValue("grantType");
//		tokenUrl=WeixinConnectConfig.getValue("accessTokenURL")+"?appid="+appid+"&secret="+secret+"&code="+code+"&grant_type="+grant_type;
//		log.info("code===========================================================" + tokenUrl);
//		String tokenInfo=HttpXmlClient.get(tokenUrl);
//		log.info("token===========================================================" + tokenInfo);
//		JSONObject json=JSONObject.parseObject(tokenInfo);
//		String accessToken=json.getString("access_token");
//		String openid=json.getString("openid");
//		String userUrl=WeixinConnectConfig.getValue("getUserInfoURL")+"?access_token="+accessToken+"&openid="+openid;
//		String userInfoFromUrl=HttpXmlClient.get(userUrl);
//		log.info("userinfo===========================================================" + userInfoFromUrl);
//		if(operateService.booleanWeixinLogin(openid)==false){
//			   //生成账号，插入数据
//				UserOperate user=prepareUserInfoWeixinInsert(openid,userInfoFromUrl);
//				boolean is=userOperateDao.regist(user);
//				if(is==true){
//					List<UserOperate> userOp=userOperateDao.finduserByOpenweixin(openid);
//	        		for (UserOperate userInfo : userOp) {
//						userInfo.setUserPassword("");
//						jsonResult.setData(userInfo);
//						if(log.isDebugEnabled()){
//							log.debug("login user :"+userInfo);
//						}
//	        		}
//				   
//					jsonResult.setStatus(UserStatusEnum.USER_LOGIN_SUC.getCode());
//					jsonResult.setDesc(UserStatusEnum.USER_LOGIN_SUC.getDescription());
//				}else{
//					jsonResult.setStatus(UserStatusEnum.USER_SANFANGLOGIN_FAI.getCode());
//					jsonResult.setDesc(UserStatusEnum.USER_SANFANGLOGIN_FAI.getDescription());
//				}
//			}else{
//				
//				String updateRsult = null;
//				List<UserOperate> userUpdate=userOperateDao.finduserByOpenweixin(openid);
//				//更新Token
//				for (UserOperate userId : userUpdate) {// 得到用户对象
//					updateRsult = operateService.updateLoginToken(userId);// 调用方法更新登录信息（令牌、有效期）
//				}
//			        	
//				if (updateRsult.equals(UserStatusEnum.USER_UPDATETOKEN_SUC.getCode())) {  
//			   		List<UserOperate> userOp=userOperateDao.finduserByOpenweixin(openid);
//	        		for (UserOperate userInfo : userOp) {
//						userInfo.setUserPassword("");
//						jsonResult.setData(userInfo);
//						if(log.isDebugEnabled()){
//							log.debug("login user :"+userInfo);
//						}
//	        		}
//				   
//					jsonResult.setStatus(UserStatusEnum.USER_LOGIN_SUC.getCode());
//					jsonResult.setDesc(UserStatusEnum.USER_LOGIN_SUC.getDescription());
//	        	}else{
//	        		jsonResult.setStatus(UserStatusEnum.USER_SANFANGLOGIN_UPDATEFAIL.getCode());
//					jsonResult.setDesc(UserStatusEnum.USER_SANFANGLOGIN_UPDATEFAIL.getDescription());
//	        	}
//				
//				}
//		ModelAndView mav  = new ModelAndView("/unify/weixinlogin");
//		int s=1;
//		mav.addObject("status",jsonResult.getStatus());
//		mav.addObject("message",net.sf.json.JSONObject.fromObject(jsonResult).toString());
//		return mav;
//	}
	/**
	 * qqLogoin
	 */
	@SuppressWarnings("null")
	@RequestMapping("/afterlogin")
	public ModelAndView toLoginSuccess(HttpServletRequest request,HttpServletResponse response) {
		JSONResult jsonResult = new JSONResult();
		UserInfo qzoneUserInfo=null;
		UserInfoBean userInfoBean=null;
		String accessToken = null;
	    String openID = null;
	    String state=(String) request.getSession().getAttribute("state");
		log.info("state----------------------------------------------"+state);
		
		try {
			
			AccessToken accessTokenObj = (new Oauth())
					.getAccessTokenByRequest(request);
			long tokenExpireIn = 0L;
			if (accessTokenObj.getAccessToken().equals("")) {
				// 我们的网站被CSRF攻击了或者用户取消了授权
				// 做一些数据统计工作zai 
				System.out.print("没有获取到响应参数");
			} else {
				accessToken = accessTokenObj.getAccessToken();
				tokenExpireIn = accessTokenObj.getExpireIn();

				// 利用获取到的accessToken 去获取当前用的openid -------- start
				OpenID openIDObj = new OpenID(accessToken);
				openID = openIDObj.getUserOpenID();

				//request.getSession().setAttribute("feixun_openid", openID);

				// 利用获取到的accessToken 去获取当前用户的openid --------- end

				qzoneUserInfo = new UserInfo(accessToken, openID);
				userInfoBean = qzoneUserInfo.getUserInfo();
			
		
			}
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		if(state.equals("login")){

			if(operateService.booleanQQLogin(openID)==false){
			   //生成账号，插入数据
				UserOperate user=prepareUserInfoQQInsert(openID,userInfoBean);
				boolean is=userOperateDao.regist(user);
				if(is==true){
					List<UserOperate> userOp=userOperateDao.finduserByOpenqq(openID);
	        		for (UserOperate userInfo : userOp) {
						userInfo.setUserPassword("");
						jsonResult.setData(userInfo);
						if(log.isDebugEnabled()){
							log.debug("login user :"+userInfo);
						}
	        		}
				   
					jsonResult.setStatus(UserStatusEnum.USER_LOGIN_SUC.getCode());
					jsonResult.setDesc(UserStatusEnum.USER_LOGIN_SUC.getDescription());
				}else{
					jsonResult.setStatus(UserStatusEnum.USER_SANFANGLOGIN_FAI.getCode());
					jsonResult.setDesc(UserStatusEnum.USER_SANFANGLOGIN_FAI.getDescription());
				}
			}else{
				
				String updateRsult = null;
				List<UserOperate> userUpdate=userOperateDao.finduserByOpenqq(openID);
				//更新Token
				for (UserOperate userId : userUpdate) {// 得到用户对象
					updateRsult = operateService.updateLoginToken(userId);// 调用方法更新登录信息（令牌、有效期）
				}
			        	
				if (updateRsult.equals(UserStatusEnum.USER_UPDATETOKEN_SUC.getCode())) {  
			   		List<UserOperate> userOp=userOperateDao.finduserByOpenqq(openID);
	        		for (UserOperate userInfo : userOp) {
						userInfo.setUserPassword("");
						jsonResult.setData(userInfo);
						if(log.isDebugEnabled()){
							log.debug("login user :"+userInfo);
						}
	        		}
				   
					jsonResult.setStatus(UserStatusEnum.USER_LOGIN_SUC.getCode());
					jsonResult.setDesc(UserStatusEnum.USER_LOGIN_SUC.getDescription());
	        	}else{
	        		jsonResult.setStatus(UserStatusEnum.USER_SANFANGLOGIN_UPDATEFAIL.getCode());
					jsonResult.setDesc(UserStatusEnum.USER_SANFANGLOGIN_UPDATEFAIL.getDescription());
	        	}
				
				}
			
				ModelAndView mav  = new ModelAndView("/unify/qqlogin");
				mav.addObject("message",net.sf.json.JSONObject.fromObject(jsonResult).toString());
				mav.addObject("status",jsonResult.getStatus());
				return mav;
		}else{
			ModelAndView mav  = new ModelAndView("/unify/qqbind_show");
			List<UserOperate> userUpdate=userOperateDao.finduserByOpenqq(openID);
			String phicommId=(String) request.getSession().getAttribute("phicommId");
			UserOperate user=new UserOperate();
			user=operateService.findUserByPhicommId(phicommId);
			if(userUpdate.isEmpty() ){
				user.setOpenqq(openID);
				userOperateDao.updateLoginInfo(user);
				jsonResult.setStatus("001");
				jsonResult.setDesc("绑定成功");
			}else{
				jsonResult.setStatus("000");
				jsonResult.setDesc("绑定失败，该账号已经被使用过");
			}
			mav.addObject("desc",jsonResult.getDesc());
			return mav;
		}
	}
	
	
	
	
	
	private UserOperate prepareUserInfoQQInsert(String openqq, UserInfoBean userInfoBean) {
		UserOperate user = new UserOperate();
		user.setOpenqq(openqq);
		user.setNickname(userInfoBean.getNickname());
		user.setSex(userInfoBean.getGender());
		user.setFigureurl(userInfoBean.getAvatar().getAvatarURL100());
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
		return user;
	}
	private UserOperate prepareUserInfoWeixinInsert(WeiXinUserInfo weiXinUserInfo) {
		UserOperate user = new UserOperate();
		user.setOpenweixin(weiXinUserInfo.getUnionid());
		user.setNickname(weiXinUserInfo.getNickname());
		if(weiXinUserInfo.getSex().equals("1")){
			user.setSex("男");
		}
		if(weiXinUserInfo.getSex().equals("0")){
			user.setSex("女");
		}
		user.setFigureurl(weiXinUserInfo.getHeadimgurl());
		user.setCity(weiXinUserInfo.getCity());
		user.setProvince(weiXinUserInfo.getProvince());
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
		return user;
	}
	
}
