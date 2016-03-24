package com.fx.mobile.user.constants.enums;


/**
 * @author jinhua.li
 * @2014年10月14日  下午2:53:36
 */
public enum UserStatusEnum {
	
	//1、验证码
	/**
	 * 获取验证码成功
	 */
    VALIDATE_CODE_SUC("001", "获取验证码成功"),
    /**
     * 获取验证码失败
     */
    VALIDATE_CODE_ERR("000", "获取验证码失败"),
/**
 * 用户已经存在
 */
    USER_EXISTED("002", "用户已存在"),
    
    //2、注册
    /**
     * 用户注册成功
     */
    USER_REGIT_SUC("001", "用户注册成功"),
    /**
     * 用户注册验证码成功
     */
    USER_REGITCODE_SUC("001", "用户注册验证码验证成功"),
    /**
     * 用户注册验证码过期
     */
    USER_REGITCODE_EXPIRED("031", "用户注册验证码过期"),
    /**
     * 验证码验证失败
     */
    USER_REGITCODE_FAIL("000", "验证码验证失败"),
    /**
     * 用户注册验证码错误或者为空，有可能是服务重启。
     */
    USER_REGITCODE_ERROR("000", "用户注册验证码错误或者为空，有可能是服务重启。"),
    /**
     * 用户注册失败
     */
    USER_REGIT_FAIL("000", "用户注册失败"),
    
    //3、登录
    /**
     * 用户不存在
     */
    USER_NOT_EXISTED("000", "用户不存在"),
    
    /**
     * 用户登录成功 ,自然跟新令牌成功
     */
    USER_LOGIN_SUC("001", "用户登录成功 "),
    /**
     * 三方登陆插入数据失败
     */
    USER_SANFANGLOGIN_FAI("002","用户生成账号失败，请重新登陆"),
    /**
     * 三方登录更新令牌失败
     */
    USER_SANFANGLOGIN_UPDATEFAIL("003","用户更新令牌失败请重新登录"),
    /**
     * 用户绑定qq并登陆成功，自然更新令牌成功
     */
    USER_QQBIND_SUCCESS("001","用户绑定 并登陆成功"),
    /**
     * 用户登录失败  
     */
    USER_LOGIN_FAIL("000", "用户登录失败 "),
    /**
     * 用户登录密码错误
     */
    USER_LOGIN_PWD_ERROR("000", "用户登录密码错误 "),
    /**
     * 用户名错误
     */
    
    USER_LOGIN_USENAME_ERROR("000", "用户名错误 "),
    /**
     * 账号错误
     */
    
    USER_LOGIN_USER_ERROR("021", "账号错误 "),
    /**
     * 密码错误
     */
    USER_LOGIN_PASS_ERROR("022", "密码错误 "),
    
    //4、重置密码
    /**
     * 重置密码成功
     */
    USER_UPDATEPWD_SUC("001", "重置密码成功"),
    /**
     * 重置密码失败
     */
    
    USER_UPDATEPWD_FAIL("000", "重置密码失败"),
    /**
     * 密码失败
     */
    
    USER_UPDATEPWD_WRONGPWD("000", "密码失败"),
    
    
    
    
    
    /**
     * 用户更新令牌成功
     */
    USER_UPDATETOKEN_SUC("007", "用户更新令牌成功 "),
    /**
     * 用户更新令牌异常
     */
    
    USER_UPDATETOKEN_FAIL("005", "用户更新令牌异常"),
    //------注册-------------------------
    /**
     * 用户注册异常
     */
    
    USER_REGIT_EXCEPTION("005", "用户注册异常"),
    
    //----更新用户信息----------------------
    /**
     * 修改用户信息成功
     */
    USER_UPDATEINFO_SUC("001", "修改用户信息成功"),
    /**
     * 修改用户信息失败
     */
    
    USER_UPDATEINFO_FAIL("000", "修改用户信息失败"),    
 
    //---------根据 keyvalue更新数据库---------------------
    /**
     * 根据key修改用户信息成功
     */
    USER_UPDATEBYKEY_SUC("001","设置成功"),
    /**
     * 根据key修改用户信息失败
     */
    
    USER_UPDATEBYKEY_FAIL("000","设置失败失败"),
    /**
     * 不能是空值
     */
    USER_UPDATEBYKEY_NULL("000","不能是空值"),
    
    //-----------邮箱注册------------------------------------
    USER_SENDMAIL_SUC("001","注册成功并且验证邮件发送成功"),
    USER_SENDMAIL_FAIL("009","注册成功但验证邮件发送失败"),
    USER_UPDATECODE_FAIL("008","更新激活码失败"),
    USER_REGISTERMAIL_USERNAMEEXIT("002","用户名已经存在"),
    USER_REGISTERMAIL_USERMAILEXIT("003","邮箱已经存在"),
    USER_REGISTERMAIL_FAIL("000","邮箱注册失败"),
    USER_ACTIVATION_FAIL("010","账号邮箱未激活,请查看邮箱"),
   
    /**
     * 邮箱不存在
     */
    USERMAIL_NOT_EXISTED("011","邮箱不存在"),

    /**
     * 重置邮件发送成功
     */
    RESTMAIL_SUC("001","重置邮件发送成功，登陆邮箱查看"),
    /**
     * 重置邮件发送失败
     */ 
    RESTMAIL_FAIL("012","重置邮件发送失败，请重试"),

//
//    CANCEL(4, "交易取消"), // 全额退款订单也标记为取消状态
//
//    FINISH(5, "交易成功"),

//    COMPLETE_WITH_REFUND(5, "完成(有退款)")
    ;

    private String code;
    private String description;

    UserStatusEnum(String code, String description) {
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
