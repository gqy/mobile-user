package com.fx.mobile.user.h5.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fx.mobile.bean.JSONResult;
import com.fx.mobile.model.UserOperate;
import com.fx.mobile.user.action.BaseAction;
import com.fx.mobile.user.dao.UserOperateDao;
import com.fx.mobile.user.service.ResourceProperties;
import com.fx.mobile.user.service.UserOperateService;
@Controller
@RequestMapping(value = "/generalunify")
public class UnifiedAccountNumbertNoInterceptorController extends BaseAction{
	@Autowired
	public  ResourceProperties urlProperties;
	@Autowired
	private UserOperateService operateService;
	@Autowired
	public UserOperateDao userOperateDao;
	
	/**
	 * 重置密码页面
	 */
	@RequestMapping("/tofindpassreset")
	public ModelAndView toFindPassReset(HttpServletResponse response,HttpServletRequest request){
		ModelAndView mav  = new ModelAndView("/unify/find_pass_reset");
		String phicommId=request.getParameter("phicommId");
		mav.addObject("phicommId",phicommId);
		return mav;
	}
	/**
	 * 登录首页
	 */
	@RequestMapping("/tologin")
	public ModelAndView toLogin(HttpServletResponse response) {
		ModelAndView mav  = new ModelAndView("/unify/login");
		return mav;
	}
	
	/**
	 * 注册手机首页
	 */
	@RequestMapping("/toregisterphone")
	public ModelAndView toRegistMobile(HttpServletResponse response) {
		ModelAndView mav  = new ModelAndView("/unify/register_phone");
		return mav;
	}
	/**
	 * 注册邮箱首页
	 */
	@RequestMapping("/toregistermail")
	public ModelAndView toRegistMail(HttpServletResponse response) {
		ModelAndView mav  = new ModelAndView("/unify/register_mail");
		return mav;
	}
	/**
	 * 找回密码首页
	 */
	@RequestMapping("/tofindpassmail")
	public ModelAndView toFindPassMail(HttpServletResponse response) {
		ModelAndView mav  = new ModelAndView("/unify/find_pass_mail");
		return mav;
	}
	@RequestMapping("/tofindpassphone")
	public ModelAndView toFindPassPhone(HttpServletResponse response) {
		ModelAndView mav  = new ModelAndView("/unify/find_pass_phone");
		return mav;
	}
  
	/**
	 * 邮箱激活码
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value="/aftermailsendurl",method = { RequestMethod.GET})
	@ResponseBody
	public ModelAndView afterMailSend(HttpServletRequest request,HttpServletResponse response) {
       String mail=request.getParameter("mail");
       String mailActivationCode=request.getParameter("mailActivationCode");
       String mailActivationCodeExpire=request.getParameter("mailActivationCodeExpire");
       DateFormat df = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
       ModelAndView mav=null;
    	try {
    		   Date date1 = df.parse(mailActivationCodeExpire);
		       Date date2 = new Date();// 得到用户当前登录时间
			   Long dif = date2.getTime() - date1.getTime();// 得到两者之间的时间差
			   Long day = dif / (1000 * 60 * 60 * 24);// 转换成天
			   if (day >1){
				   mav  = new ModelAndView("/unify/active_fail");
			   }else{
				   UserOperate user=userOperateDao.findUserByUserMail(mail).get(0);
				   if(user.getIsMailActivated()==1){
					   mav  = new ModelAndView("/unify/active_have_success");
				   }else{
					   if(mailActivationCode.equals(user.getMailActivationCode())){
						   Long a=1L;
						   boolean is=userOperateDao.updateUserOperateIsMailActivated(a,mail);
						   if(is==true){
							   mav  = new ModelAndView("/unify/active_success");
						   }else{
							   mav  = new ModelAndView("/unify/active_exception");
						   }
						 
					   }else{
						   mav  = new ModelAndView("/unify/active_exception");
					   }
				   }
			   }
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			mav  = new ModelAndView("/unify/active_exception");
			e.printStackTrace();
		}
    	return mav;
	}
}
