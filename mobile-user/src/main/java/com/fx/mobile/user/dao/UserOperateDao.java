package com.fx.mobile.user.dao;

import java.util.List;

import com.fx.mobile.model.UserOperate;


public interface UserOperateDao {

	public int updateUserPwd(String mobileNumber,String newPwd);
	/**
	 * 登录用户或检测用户是否存在（用户名和密码登录）
	 * @param user
	 * @return
	 */
	public List<UserOperate> longin(UserOperate user);

	/**
	 * 根据qqId（数据库openqq）进行三方登录使用三方登录使用
	 * @param openqq
	 * @param password
	 * @return
	 */
	public List<UserOperate> longinQQ(UserOperate user);
	
	/**
	 * 注册账户
	 * @param UserOperate  这里传入对象，获取到页面传的值赋给对象；
	 * ********************可以统一调用这个接口进行注册，可用注册参数邮箱、用户名、手机号+密码
	 * @return
	 */
	public boolean regist(UserOperate user);
	public String registAccount(UserOperate user); 
	
	/**
	 * 查询用户是否存在
	 * @param UserOperate 这里传入对象，获取到页面传的值赋给对象；
	 * ********************可以统一调用这个接口进行查询，参数可用邮箱、用户名、手机号,仅对登录有效，user.getUserPhoneNumb()必须有值，user.getUserPhoneNumb()代表用户名，邮箱和手机号
	 * @return
	 */
	public List<UserOperate> findUsers(UserOperate user);
	public List<UserOperate> findUsersByMail(UserOperate user);
	public List<UserOperate> findUsersByUserName(UserOperate user);
	public List<UserOperate> findUsersByPhone(UserOperate user);
	
	/**
	 * 
	 * 查询用户是否存在
	 * @author jinhua.li
	 * @Date 2015年4月7日15:08:20
	 * @param UserOperate 这里传入对象，获取到页面传的值赋给对象；
	 * ********************可以统一调用这个接口进行查询，参数可用邮箱、用户名、手机号
	 * @return
	 */
	public boolean isExistUser(UserOperate user);
	public boolean isExistUserByUserName(UserOperate user);
	public boolean isExistUserByMail(UserOperate user);
	
	/**
	 * 查找最后一个插入的phicommId ；目的：需要处理PhicommId号，每次加1使每个用户都有不同的PhicommId可登录
	 * @return
	 */
	public List<UserOperate> findEndPhicommId();
	
	/**
	 * （更新用户信息）每次输入用户名和密码登录后，需要修改登录令牌，和过期时间 (用户对象修改)
	 * @param user
	 */
	public void updateLoginInfo(UserOperate user);
	
	/**
	 * 用户二次登录（根据openId和tokenId以及过期）
	 * @param userOperate
	 * @return
	 */
	public List<UserOperate> getSempUser(UserOperate userOperate);
	

	/**
	 * 根据唯一id查询用户信息
	 * @param openId
	 * @return
	 */
	public List<UserOperate> findUserByOpenId(String openId);
	
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
	 * 根据破qq返回的openId(数据库openqq字段)查询用户
	 */
	public List<UserOperate> finduserByOpenqq(String openqq);
	/**
	 * 根据weixinId查询
	 * @param openweixin
	 * @return
	 */
	public List<UserOperate> finduserByOpenweixin(String openweixin) ;
	/**
	 * 根据name查询出是否存在用户，name可以是手机号，邮箱或者用户名
	 * @param name
	 * @return
	 */
		
	public List<UserOperate> findUserByName(String name);
	/**
	 * 根据name（可以是手机号邮箱或者用户名）和密码查询出用户，可用于登录使用
	 */
	public List<UserOperate> findUserByNameAndPass(String name,String pass);
    
	  /**
     * 根据手机号或者邮箱或者用户名更新qq三方登录信息更新qqinfo，用于qq三方登录第一次绑定账号
     */
	public void updateQQInfoByUserNameOrMailOrPhoneNumber(UserOperate userOp);
	/**
	 * 更新
	 * @param user
	 */
	public void update(UserOperate user);
	/**
	 * 根据数据库表字段名和value值更新数据
	 */
	public boolean updateUserOperateByKey(String key,String value,String phicomm_id);
	/**
	 * 根据两个数据库表字段名和value值更新数据
	 */
	public boolean updateUserOperateByKey2(String key1,String value1,String key2,String value2,String phicomm_id);
	/**
	 * 根据phicomm_id查询用户信息
	 * @param phicomm_id
	 * @return
	 */
	public List<UserOperate> findUserByPhicommId(String phicomm_id);
	/**
	 * 根据用户名查询用户
	 */
	public List<UserOperate> findUserByUserName(String username);
	/**
	 * 根据邮箱查询用户
	 */
	public List<UserOperate> findUserByUserMail(String mail);
	/**
	 * 更新邮箱激活标识字段
	 * @param is
	 * @param mail
	 * @return
	 */
	boolean updateUserOperateIsMailActivated(Long is, String mail);
}
