package com.fx.mobile.user.constants.enums;


/**
 * @author jinhua.li
 * @2014年10月14日  下午2:53:36
 */
public enum BizExceptionEnum {
	
	//1、验证码
    PARAM_CODE_EXCEPTION("000", "参数缺失"),
   
    ;

    private String code;
    private String description;

    BizExceptionEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
  
}
