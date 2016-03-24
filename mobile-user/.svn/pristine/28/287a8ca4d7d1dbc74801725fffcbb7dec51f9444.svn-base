package com.fx.mobile.user.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import org.apache.log4j.Logger;

import com.fx.mobile.bean.JSONResult;
import com.fx.mobile.user.constants.enums.UserStatusEnum;


public class BaseAction  {

	private static final long serialVersionUID = 6947577092058412010L;

	protected Logger log = Logger.getLogger(this.getClass());
    
    public Map resultMap = new HashMap();
    
    private HttpServletRequest request;
    
	private HttpServletResponse response;
	
	protected static final String JSONOBJ2STR ="jsonObject" ;
    
    protected HttpServletRequest getRequest()  {
       
        return request;
    }
    protected HttpServletResponse getResponse() {

        return response;
    }
    
    
    protected JSONResult setRtnResult(UserStatusEnum userEnum) {
		JSONResult jsonResult = new JSONResult();
		jsonResult.setStatus(userEnum.getCode());
		jsonResult.setDesc(userEnum.getDescription());
		return jsonResult;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
    
    
    

}
