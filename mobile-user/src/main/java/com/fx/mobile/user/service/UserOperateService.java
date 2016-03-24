package com.fx.mobile.user.service;

import java.util.List;

import com.fx.mobile.model.UserOperate;


public interface UserOperateService {
	public int updateUserPwd(String mobileNumber,String newPwd);
	/**
	 * 登录用户或检测用户是否存在
	 * @param user
	 * @return
	 */
	public String login(UserOperate user);
	/**
	 * qq三方登录，用qqId(数据库qqInfo字段)
	 * @param user
	 * @return
	 */
	public String loginQQ(UserOperate user);
	
	/**
	 * 注册账户
	 * @param user
	 * @return
	 */
	public String registAccount(UserOperate user) throws Exception; 
	
	/**
	 * 查询用户
	 * @param user
	 * @return
	 */
	public String findUsers(UserOperate user);
	
	/**
	 * 用户注册完后根据用户注册信息如：手机号、Email或用户名。查找并返回注册的相关信息给客户端进行指令验证登录
	 * @param user
	 * @return
	 */
	public List<UserOperate> findRegistInfoBackClien(UserOperate user);
	
	/**
	 * （更新用户信息）每次输入用户名和密码登录后，需要修改登录令牌，和过期时间 
	 * @param user
	 */
	public String updateLoginToken(UserOperate user);
	/**
	 * 更新邮箱激活码
	 * @param user
	 * @return
	 */
	public String updateMailActivationCode(UserOperate user);
	
	public List<UserOperate> getSempUser(UserOperate userOperate);
	
	
	/**
	 * 根据唯一id查询用户信息
	 * @param openId
	 * @return
	 */
	public UserOperate findUserByOpenId(String openId);
	
	
	/**
	 * 根据用户唯一id修改（上传）用户头像
	 * @param openid
	 */
	public void updateUserHeadPortraitByOpenid(String openid,String headPortraitPic);
	
	/**
	 * 修改用户基本信息
	 * @param openId
	 */
	public void updateUserInfo(UserOperate userOp);
	/**
	 * 判断三方登录是否可以直接登录
	 * @param openqqId
	 */
	public boolean booleanQQLogin(String openqqId);
	/**
	 * 判断微信三方登录是否可以直接登录
	 * @param openweixin
	 * @return
	 */
	public boolean booleanWeixinLogin(String openweixin);
	
	/**
	 * 判断用户是否存在,可以是手机号
	 * @param name
	 */
	public boolean booleanUserIsExist(String name);
	
    
	  /**
     * 根据手机号或者邮箱或者用户名更新qq三方登录信息更新qqinfo，用于qq三方登录第一次绑定账号
     */
	public void updateQQInfoByUserNameOrMailOrPhoneNumber(UserOperate userOp);
	/**
	 * 根据破qq返回的openId(数据库openqq字段)查询用户
	 */
	public List<UserOperate> finduserByOpenqq(String openqq);
	/**
	 * 根据keyvalue更新单个字段
	 * @param key
	 * @param value
	 */
	public boolean updateUserOperateByKey(String key,String value,String phicomm_id);
	/**
	 * 根据两个KeyValue更新两个字段 
	 * @param key1
	 * @param value1
	 * @param key2
	 * @param value2
	 * @param phicomm_id
	 * @return
	 */
	public boolean updateUserOperateByKey2(String key1,String value1,String key2,String value2,String phicomm_id);
	/**
	 * 根据phicommid查询数用户信息
	 * @param phicomm_id
	 * @return
	 */
	public UserOperate findUserByPhicommId(String phicomm_id);
	/**
	 * 邮箱注册
	 * @param user
	 * @return
	 */
	public String mailRegister(UserOperate user);
	/**
	 * 邮箱是否被激活
	 * @param user
	 * @return
	 */
	 public boolean isMailActivated(String mailoruser);
	 /**
	  * 发送重置邮件
	  * @param user
	  * @return
	  */
	boolean resetMail(UserOperate user);
}
