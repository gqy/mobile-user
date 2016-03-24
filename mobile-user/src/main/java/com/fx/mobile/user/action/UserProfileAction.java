package com.fx.mobile.user.action;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fx.mobile.bean.JSONResult;
import com.fx.mobile.model.UserOperate;
import com.fx.mobile.user.constants.enums.UserStatusEnum;
import com.fx.mobile.user.service.ResourceProperties;
import com.fx.mobile.user.service.UserOperateService;

@Controller
@RequestMapping(value = "/profile")
public class UserProfileAction extends BaseAction {
	
	@Autowired
	private UserOperateService operateService;
	
	@Autowired
	public  ResourceProperties resourceProperties;
	
	@RequestMapping(value = "/updateUserInfo",method = { RequestMethod.POST})
	@ResponseBody
	public JSONResult updateUserInfo(HttpServletRequest request) throws UnsupportedEncodingException {
		log.debug("调用更新用户信息接口,参数==="+request.getQueryString());
		String id = request.getParameter("id");
		String userName = request.getParameter("username");
		String nickname = request.getParameter("nickname");
		String sex = request.getParameter("sex");
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		String openId = request.getParameter("openId");
		String birthday = request.getParameter("birthday");
		String userPhoneNumb = request.getParameter("userPhoneNumb");

		UserOperate userOp = new UserOperate();
		userOp.setOpenId(openId);
		userOp.setUserName(userName);
		userOp.setUserPhoneNumb(userPhoneNumb);

		JSONResult jsonResult = null;
		try {
			
			userOp.setNickname(nickname);
			userOp.setSex(sex);
			userOp.setProvince(province);
			userOp.setCity(city);
			userOp.setBirthday(birthday);
			operateService.updateUserInfo(userOp);
			userOp = operateService.findUserByOpenId(openId);
		    userOp.setUserPassword("");
		    userOp.setFigureurl(resourceProperties.getFileHttpUrl()+userOp.getFigureurl());
			if(log.isDebugEnabled()){
				log.debug("login user :"+userOp);
			}
			 jsonResult = setRtnResult(UserStatusEnum.USER_UPDATEINFO_SUC);
			 jsonResult.setData(userOp);
		} catch (Exception e) {
			log.error(e, e);
			jsonResult = setRtnResult(UserStatusEnum.USER_UPDATEINFO_FAIL);
		}

		return jsonResult;
	}
	



	
}
