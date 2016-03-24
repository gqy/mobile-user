package com.fx.mobile.user.h5.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fx.mobile.model.UserOperate;
import com.fx.mobile.user.action.BaseAction;
import com.fx.mobile.user.dao.UserOperateDao;
import com.fx.mobile.user.service.ResourceProperties;
import com.fx.mobile.user.service.UserOperateService;

@Controller
@RequestMapping(value = "/unify")
public class UnifiedAccountNumberController extends BaseAction{
	@Autowired
	public  ResourceProperties resourceProperties;
	@Autowired
	private UserOperateService operateService;
	@Autowired
	public UserOperateDao userOperateDao;

	/**
	 * 用户编辑首页
	 */
	@RequestMapping("/topersoninfo")
	public ModelAndView toPersonInfo(HttpServletRequest request,HttpServletResponse response) {
		String phicommid=request.getParameter("phicommid");
		System.out.println("phicommmid------------------------------------------"+phicommid);	
		ModelAndView mav  = new ModelAndView("/unify/person_info");
		UserOperate userOperate=operateService.findUserByPhicommId(phicommid);
		mav.addObject("phicomm_id", userOperate);
		return mav;
	}
	/**
	 * 修改密码页面
	 */
	@RequestMapping("/topassreset")
	public ModelAndView toPassReset(HttpServletResponse response,HttpServletRequest request){
		ModelAndView mav  = new ModelAndView("/unify/pass_reset");
		String phicommId=request.getParameter("phicommId");
		String openId=request.getParameter("openId");
		String expireseIn=request.getParameter("expireseIn");
		String accessToken=request.getParameter("accessToken");
		mav.addObject("phicommId",phicommId);
		mav.addObject("openId",openId);
		mav.addObject("expireseIn",expireseIn);
		mav.addObject("accessToken",accessToken);
		return mav;
	}
	/**
	 * 到三房账号绑定页面
	 */
	@RequestMapping("/tothreebind")
	public ModelAndView toThreeBind(HttpServletResponse response,HttpServletRequest request){
		ModelAndView mav  = new ModelAndView("/unify/three_bind");
		String phicommId=request.getParameter("phicommid");
		UserOperate userOperate=operateService.findUserByPhicommId(phicommId);
		mav.addObject("phicomm_id", userOperate);
//		String phicommId=request.getParameter("phicommId");
//		mav.addObject("phicommId",phicommId);
		return mav;
	}
	
}
