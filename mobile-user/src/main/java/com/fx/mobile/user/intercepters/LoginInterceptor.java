package com.fx.mobile.user.intercepters;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fx.mobile.model.UserOperate;
import com.fx.mobile.user.action.BaseAction;
import com.fx.mobile.user.service.UserOperateService;

/**
 * 登录拦截器
 * 
 * @author jinhua.li
 * @2014年9月29日 下午6:29:57
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	private static final String ERRORSTATUS = "000";
	
	
	@Autowired
	private UserOperateService operateService;
	/**
	 * 
	 */

	Logger logger = Logger.getLogger(this.getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	
		if (handler instanceof HandlerMethod) {//handler是否是HandlerMethod的实例
			HandlerMethod hm = (HandlerMethod) handler;
			BaseAction baseCtl = (BaseAction) hm.getBean();
			baseCtl.setRequest(request);
			baseCtl.setResponse(response);
			logger.debug("将用户请求清晰注入到BaseCtl...");
			// 登录拦截
			return filterLogin(request, response, baseCtl);
			} else {
			return true;
			}
	
	}

	/**
	 * 判断是否登录，如果不登录则跳转到登录页面
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private boolean filterLogin(HttpServletRequest request, HttpServletResponse response, BaseAction baseCtl)
			throws IOException, ServletException {
		boolean  islogined = false;
		String tokenId = request.getParameter("accessToken");// 令牌
		String openId = request.getParameter("openId");// openId
		String expireseIn = request.getParameter("expireseIn");// 得到用户上次登录的时间
		logger.debug("Intercept..........................................................."+"tokenid:"+tokenId+"openId:"+openId+"expireseIn:"+expireseIn);
		if(StringUtils.isBlank(tokenId) || StringUtils.isBlank(openId)||StringUtils.isBlank(expireseIn)){
			logger.error("parameter is  null ,please check parameter.");
			Enumeration  paraNames = request.getParameterNames();
			StringBuffer sb = new StringBuffer();
			for(Enumeration e=paraNames;e.hasMoreElements();){
				 
			       String thisName=e.nextElement().toString();
			       String thisValue=request.getParameter(thisName);
			      sb.append(thisName).append(":").append(thisValue);
			 
			}
			response.sendRedirect(getContextPath(request)+"/generalunify/tologin");
			return false;
		}
		// 判断天数是否已经过期
		UserOperate userOperate = new UserOperate();      
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
		       //大于90天就需要重新登录
				 islogined = false ;
			} else {
				// 调用业务，判断用户是否存在
				List<UserOperate> listResult = operateService.getSempUser(userOperate);
				islogined = (listResult.size() > 0) ? true : false;
			
			}
		} catch (Exception e) {
			logger.error(e,e);
			 islogined = false;
		}
		if(!islogined){
			response.sendRedirect(getContextPath(request)+"/generalunify/tologin");
			return false;
		}
		return islogined;
	}


	
	/**
	 * 获取URL请求的URL
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private String getRequestUrl(HttpServletRequest request) throws UnsupportedEncodingException {
		String basePath = getContextPath(request)+request.getServletPath();

		Enumeration<String> params = request.getParameterNames();
		StringBuffer paraStr = new StringBuffer("?");
		while (params.hasMoreElements()) {
			String name = params.nextElement();
			String value = request.getParameter(name);
			paraStr.append(name).append("=").append(value).append("&");
		}
		if (paraStr.lastIndexOf("&") != -1) {
			paraStr.deleteCharAt(paraStr.lastIndexOf("&"));
		}
		if (paraStr.toString().endsWith("?")) {
			paraStr.deleteCharAt(paraStr.lastIndexOf("?"));
		}
		return URLEncoder.encode(basePath +paraStr, "UTF-8");
	}

	private String getContextPath(HttpServletRequest request) {
		String basePath = request.getScheme()+"://"+request.getServerName();
		if(80!=request.getServerPort()){
			basePath += ":"+request.getServerPort();
		}
		basePath += request.getContextPath();
		return basePath;
	}
}
