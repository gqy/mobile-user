/*
 * Powered By [funxun.com]
 * Since 2014 - 2015
 */

package com.fx.mobile.model;



/**
 * 	TODO 请用一句话描述该类的作用
 * @author lijinhua email:jinhua.li@feixun.com.cn
 * @version 1.0
 * @since 1.0
 */


public class UserOperate extends BaseEntity {
	private static final long serialVersionUID = 5454155825314635342L;


	//columns START

	private java.lang.Long id;           //"id";

	private java.lang.String userName;           //"userName";

	private java.lang.String userPassword;           //"userPassword";

	private java.lang.String phicommId;           //"斐讯id";

	private java.lang.String userPhoneNumb;           //"userPhoneNumb";

	private java.lang.String userEmail;           //"userEmail";

	private java.lang.String userType;           //"userType";

	private java.lang.String isChecked;           //"每日签到";

	private java.lang.String nickname;           //"nickname";

	private java.lang.String figureurl;           //"用户头像";

	private java.lang.String sex;           //"sex";

	private java.lang.String province;           //"province";

	private java.lang.String city;           //"city";

	private java.lang.String birthday;           //"birthday";

	private java.lang.String openId;           //"用户唯一标识";

	private java.lang.String openKey;           //"身份验证秘钥";

	private java.lang.String accessToken;           //"用户访问令牌";

	private java.lang.String expireseIn;           //"token有效期";

	private java.lang.String devinfo;           //"设备信息";

	private java.lang.String userinfo;           //"userinfo";

	private java.lang.String openqq;           //"腾讯QQ";

	private java.lang.String openqqinfo;           //"腾讯QQ信息";

	private java.lang.String openweixin;           //"新浪";

	private java.lang.String openweixininfo;           //"新浪用户信息";

//	private java.lang.Float integral;           //"积分";
//
//	private java.util.Date createTime;           //"用户注册时间";
//
//	private java.lang.Integer status;           //"用户状态";
	private java.lang.String  createTime;       //注册时间
	private java.lang.String  updateTime;       //修改时间
	private java.lang.String  lastLoginTime;     //最后登录时间
	private java.lang.String lastIp;
	private java.lang.Long loginNum;
	private java.lang.String srcAppId;
	private java.lang.Long isMailActivated=0L;
	private java.lang.String mailActivationCode;
	private java.lang.String mailActivationCodeExpireseIn;
	private java.lang.String findPassCode;
	private java.lang.String findPassCodeExpireseIn;

	
	//columns END


	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	public void setUserName(java.lang.String value) {
		this.userName = value;
	}
	
	public java.lang.String getUserName() {
		return this.userName;
	}
	public void setUserPassword(java.lang.String value) {
		this.userPassword = value;
	}
	
	public java.lang.String getUserPassword() {
		return this.userPassword;
	}
	public void setPhicommId(java.lang.String value) {
		this.phicommId = value;
	}
	
	public java.lang.String getPhicommId() {
		return this.phicommId;
	}
	public void setUserPhoneNumb(java.lang.String value) {
		this.userPhoneNumb = value;
	}
	
	public java.lang.String getUserPhoneNumb() {
		return this.userPhoneNumb;
	}
	public void setUserEmail(java.lang.String value) {
		this.userEmail = value;
	}
	
	public java.lang.String getUserEmail() {
		return this.userEmail;
	}
	public void setUserType(java.lang.String value) {
		this.userType = value;
	}
	
	public java.lang.String getUserType() {
		return this.userType;
	}
	public void setIsChecked(java.lang.String value) {
		this.isChecked = value;
	}
	
	public java.lang.String getIsChecked() {
		return this.isChecked;
	}
	public void setNickname(java.lang.String value) {
		this.nickname = value;
	}
	
	public java.lang.String getNickname() {
		return this.nickname;
	}
	public void setFigureurl(java.lang.String value) {
		this.figureurl = value;
	}
	
	public java.lang.String getFigureurl() {
		return this.figureurl;
	}
	public void setSex(java.lang.String value) {
		this.sex = value;
	}
	
	public java.lang.String getSex() {
		return this.sex;
	}
	public void setProvince(java.lang.String value) {
		this.province = value;
	}
	
	public java.lang.String getProvince() {
		return this.province;
	}
	public void setCity(java.lang.String value) {
		this.city = value;
	}
	
	public java.lang.String getCity() {
		return this.city;
	}
	public void setBirthday(java.lang.String value) {
		this.birthday = value;
	}
	
	public java.lang.String getBirthday() {
		return this.birthday;
	}
	public void setOpenId(java.lang.String value) {
		this.openId = value;
	}
	
	public java.lang.String getOpenId() {
		return this.openId;
	}
	public void setOpenKey(java.lang.String value) {
		this.openKey = value;
	}
	
	public java.lang.String getOpenKey() {
		return this.openKey;
	}
	public void setAccessToken(java.lang.String value) {
		this.accessToken = value;
	}
	
	public java.lang.String getAccessToken() {
		return this.accessToken;
	}
	public void setExpireseIn(java.lang.String value) {
		this.expireseIn = value;
	}
	
	public java.lang.String getExpireseIn() {
		return this.expireseIn;
	}
	public void setDevinfo(java.lang.String value) {
		this.devinfo = value;
	}
	
	public java.lang.String getDevinfo() {
		return this.devinfo;
	}
	public void setUserinfo(java.lang.String value) {
		this.userinfo = value;
	}
	
	public java.lang.String getUserinfo() {
		return this.userinfo;
	}
	public void setOpenqq(java.lang.String value) {
		this.openqq = value;
	}
	
	public java.lang.String getOpenqq() {
		return this.openqq;
	}
	public void setOpenqqinfo(java.lang.String value) {
		this.openqqinfo = value;
	}
	
	public java.lang.String getOpenqqinfo() {
		return this.openqqinfo;
	}
	public void setOpenweixin(java.lang.String value) {
		this.openweixin = value;
	}
	
	public java.lang.String getOpenweixin() {
		return this.openweixin;
	}
	public void setOpenweixininfo(java.lang.String value) {
		this.openweixininfo = value;
	}
	
	public java.lang.String getOpenweixininfo() {
		return this.openweixininfo;
	}
	public java.lang.String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.lang.String createTime) {
		this.createTime = createTime;
	}

	public java.lang.String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.lang.String updateTime) {
		this.updateTime = updateTime;
	}

	public java.lang.String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(java.lang.String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
//	public void setIntegral(java.lang.Float value) {
//		this.integral = value;
//	}
//	
//	public java.lang.Float getIntegral() {
//		return this.integral;
//	}
//	public void setCreateTime(java.util.Date value) {
//		this.createTime = value;
//	}
//	
//	public java.util.Date getCreateTime() {
//		return this.createTime;
//	}
//	public void setStatus(java.lang.Integer value) {
//		this.status = value;
//	}
//	
//	public java.lang.Integer getStatus() {
//		return this.status;
//	}

	public java.lang.String getLastIp() {
		return lastIp;
	}

	public void setLastIp(java.lang.String lastIp) {
		this.lastIp = lastIp;
	}

	public java.lang.Long getLoginNum() {
		return loginNum;
	}

	public void setLoginNum(java.lang.Long loginNum) {
		this.loginNum = loginNum;
	}

	public java.lang.String getSrcAppId() {
		return srcAppId;
	}

	public void setSrcAppId(java.lang.String srcAppId) {
		this.srcAppId = srcAppId;
	}

	public java.lang.Long getIsMailActivated() {
		return isMailActivated;
	}

	public void setIsMailActivated(java.lang.Long isMailActivated) {
		this.isMailActivated = isMailActivated;
	}

	public java.lang.String getMailActivationCode() {
		return mailActivationCode;
	}

	public void setMailActivationCode(java.lang.String mailActivationCode) {
		this.mailActivationCode = mailActivationCode;
	}

	public java.lang.String getMailActivationCodeExpireseIn() {
		return mailActivationCodeExpireseIn;
	}

	public void setMailActivationCodeExpireseIn(
			java.lang.String mailActivationCodeExpireseIn) {
		this.mailActivationCodeExpireseIn = mailActivationCodeExpireseIn;
	}

	public java.lang.String getFindPassCode() {
		return findPassCode;
	}

	public void setFindPassCode(java.lang.String findPassCode) {
		this.findPassCode = findPassCode;
	}

	public java.lang.String getFindPassCodeExpireseIn() {
		return findPassCodeExpireseIn;
	}

	public void setFindPassCodeExpireseIn(java.lang.String findPassCodeExpireseIn) {
		this.findPassCodeExpireseIn = findPassCodeExpireseIn;
	}





}

