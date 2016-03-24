package com.fx.mobile.user.intercepters;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fx.mobile.model.UserOperate;
import com.fx.mobile.user.action.BaseAction;
import com.fx.mobile.user.dao.UserOperateDao;
import com.fx.mobile.user.service.UserOperateService;

public class FindPassInterceptor extends HandlerInterceptorAdapter{
	@Autowired
	private UserOperateService operateService;
	@Autowired
	public UserOperateDao userOperateDao;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {//handler是否是HandlerMethod的实例
			HandlerMethod hm = (HandlerMethod) handler;
			BaseAction baseCtl = (BaseAction) hm.getBean();
			baseCtl.setRequest(request);
			baseCtl.setResponse(response);
			// 登录拦截
			return filterPass(request, response, baseCtl);
			} else {
			return true;
			}
	}
	private boolean filterPass(HttpServletRequest request, HttpServletResponse response, BaseAction baseCtl)
			throws IOException, ServletException {
		boolean  ispassreset = false;
		String phicommId=request.getParameter("phicommId");
		String findPassCode=request.getParameter("findPassCode");
		if(phicommId!=null && !userOperateDao.findUserByPhicommId(phicommId).isEmpty()){
			UserOperate user=userOperateDao.findUserByPhicommId(phicommId).get(0);
			String code=request.getParameter("code");
			
			if(StringUtils.isBlank(code)){
				DateFormat df = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
				Date date1;
				try {
					date1 = df.parse(user.getFindPassCodeExpireseIn());
					Date date2 = new Date();// 得到用户当前登录时间
					Long dif = date2.getTime() - date1.getTime();// 得到两者之间的时间差
					System.out.println(dif);
					Long day = dif / (1000 * 60 * 60 * 24);// 转换成天
					// sb.append("[");
					if (day >= 1) {
				       //大于1天就需要重新登录
						ispassreset = false ;
					} else {
						
						if(user.getFindPassCode().equals(findPassCode)){
							ispassreset = true ;
						}
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}else{
				
				 try{
					  DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				       String codeFromSession=(String) request.getSession()
								.getAttribute("code");
					    String dateString=(String)request.getSession().getAttribute("codeTime");
					   	Date date2 = new Date();// 得到用户当前登录时间
					   	Date date1 = df.parse(dateString);       
						Long dif = date2.getTime() - date1.getTime();// 得到两者之间的时间差
						Long minute = dif / (1000 * 60 );// 转换成分钟
				       if(code.equals(codeFromSession) && minute<2){
				    	   ispassreset = true ;
				       }
				 }catch(ParseException e){
					 e.printStackTrace();
				 }
			}
		}
		
		
			
		if(ispassreset==false)
		response.sendRedirect(getContextPath(request)+"/generalunify/tofindpassmail");
		
		return ispassreset;
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
