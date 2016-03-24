package com.fx.mobile.user.action;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;


import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fx.mobile.bean.JSONResult;
import com.fx.mobile.model.UserOperate;
import com.fx.mobile.user.constants.enums.BizExceptionEnum;
import com.fx.mobile.user.constants.enums.UserStatusEnum;



@Controller
@RequestMapping(value = "/user")
public class IndexAction extends BaseAction{
	
	@RequestMapping(value="/index") 
    public ModelAndView getFirstPage(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("health");
        return mv;
    }

	/**
	 * 注册首页
	 */
	@RequestMapping("/toupdate")
	public ModelAndView toUpdate(HttpServletRequest request ,HttpServletResponse response) {
		ModelAndView mav  = new ModelAndView();
		if(request.getSession().getId()== null){
			mav.setViewName("/mreglog/mlogin");
			mav.addObject("", "");
		}

		mav.setViewName("/muser/mupdate");
		return mav;
	}
	
	@RequestMapping(value = "/msg")
	@ResponseBody
	public JSONResult getMsg(HttpServletRequest request) throws UnsupportedEncodingException {
		String queryString = request.getQueryString();
		log.debug("调用更新用户信息接口,参数==="+queryString);
		
		JSONResult jsonResult = new  JSONResult();
		try {
			 jsonResult.setStatus(BizExceptionEnum.PARAM_CODE_EXCEPTION.getCode());
			 jsonResult.setDesc(BizExceptionEnum.PARAM_CODE_EXCEPTION.getDescription());
			 jsonResult.setData(queryString);
		} catch (Exception e) {
			log.error(e, e);
			 jsonResult.setStatus("999999");
			 jsonResult.setDesc(e.toString());
		}

		return jsonResult;
	}		
}
