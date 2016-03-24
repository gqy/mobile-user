//package com.fx.mobile.user.action;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.fx.mobile.bean.JSONResult;
//import com.fx.mobile.bean.Jsonqq;
//import com.fx.mobile.user.action.BaseAction;
//import com.fx.mobile.user.service.UserOperateService;
//import com.qq.connect.api.OpenID;
//import com.qq.connect.api.qzone.UserInfo;
//import com.qq.connect.javabeans.AccessToken;
//import com.qq.connect.javabeans.qzone.UserInfoBean;
//import com.qq.connect.oauth.Oauth;
//
//
//@Controller
//@RequestMapping(value = "/union")
//public class UserunionloginAction extends BaseAction {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = -72421187174779094L;
//	@Autowired
//	private UserOperateService operateService;
//	// 退出，注销用户
//	public void qqlogin() {
//		System.out.println("1231231231");
//		log.debug("qqlogin");
//	
//
//	}
//
//	@RequestMapping(value = "/toqqlogin")
//	public void toqqlogin(HttpServletRequest request, HttpServletResponse response) {
//		try {
//			System.out.println("1231231231");
//			String authorizeURL = new Oauth().getAuthorizeURL(request);
//			System.out.println("authorizeURL====" + authorizeURL);
//			response.sendRedirect(authorizeURL);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
//
////	@RequestMapping(value = "/afterlogin")
////	@ResponseBody
////	public JSONResult afterLogin(HttpServletRequest request, HttpServletResponse response) {
////		JSONResult jsonResult = new JSONResult();
////		try {
////			
////			AccessToken accessTokenObj = (new Oauth())
////					.getAccessTokenByRequest(request);
////			String accessToken = null, openID = null;
////			long tokenExpireIn = 0L;
////			if (accessTokenObj.getAccessToken().equals("")) {
////				// 我们的网站被CSRF攻击了或者用户取消了授权
////				// 做一些数据统计工作
////				System.out.print("没有获取到响应参数");
////			} else {
////				accessToken = accessTokenObj.getAccessToken();
////				tokenExpireIn = accessTokenObj.getExpireIn();
////
////				request.getSession().setAttribute("feixun_access_token",
////						accessToken);
////				request.getSession().setAttribute("feixun_token_expirein",
////						String.valueOf(tokenExpireIn));
////
////				// 利用获取到的accessToken 去获取当前用的openid -------- start
////				OpenID openIDObj = new OpenID(accessToken);
////				openID = openIDObj.getUserOpenID();
////
////				request.getSession().setAttribute("feixun_openid", openID);
////
////				// 利用获取到的accessToken 去获取当前用户的openid --------- end
////
////				UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
////				UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
////				System.out.println(userInfoBean);
////				if (userInfoBean.getRet() == 0) {
////					jsonResult.setData(userInfoBean);
////				} else {
////					jsonResult.setData( userInfoBean.getRet());
////					
////				}
////				jsonResult.setData(userInfoBean.getNickname());
////			}
////		} catch (Exception e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////
////		return jsonResult;
////	}
//	/**
//	 * qqlogin首页
//	 */
//	@SuppressWarnings("null")
//	@RequestMapping("/afterlogin")
//	public ModelAndView toRegister(HttpServletRequest request,HttpServletResponse response) {
//
//    	JSONResult jsonResult = new JSONResult();
//		UserInfo qzoneUserInfo=null;
//		UserInfoBean userInfoBean=null;
//		Jsonqq jsonqq=new Jsonqq();
//		String accessToken = null;
//		String openID = null;
//		try {
//			
//			AccessToken accessTokenObj = (new Oauth())
//					.getAccessTokenByRequest(request);
//			
//			long tokenExpireIn = 0L;
//			if (accessTokenObj.getAccessToken().equals("")) {
//				// 我们的网站被CSRF攻击了或者用户取消了授权
//				// 做一些数据统计工作
//				System.out.print("没有获取到响应参数");
//			} else {
//				accessToken = accessTokenObj.getAccessToken();
//				tokenExpireIn = accessTokenObj.getExpireIn();
//
//				// 利用获取到的accessToken 去获取当前用的openid -------- start
//				OpenID openIDObj = new OpenID(accessToken);
//				openID = openIDObj.getUserOpenID();
//
//				request.getSession().setAttribute("feixun_openid", openID);
//
//				// 利用获取到的accessToken 去获取当前用户的openid --------- end
//
//				qzoneUserInfo = new UserInfo(accessToken, openID);
//				userInfoBean = qzoneUserInfo.getUserInfo();
//				jsonqq.setOpenId(openID);
//				jsonqq.setUserInfoBean(userInfoBean);
//		
//			}
//		} catch (Exception e) {
//		
//			e.printStackTrace();
//		}
//		if(operateService.booleanQQLogin(openID)==true){
//			log.info("葛沁阳1--------------------------------------------------------------------------------------------------------------------");
//			ModelAndView mav  = new ModelAndView("/allapp/qqloginsuccess");
//			return mav;
//		
//		}else{
//			log.info("葛沁阳2--------------------------------------------------------------------------------------------------------------------"+jsonqq.toString());
//			ModelAndView mav  = new ModelAndView("/allapp/qqloginbind");
//			mav.addObject("qqinfo", jsonqq);
//			return mav;
//		}
//	}
//	
//}
