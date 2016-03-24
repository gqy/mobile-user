package com.fx.mobile.user.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fx.mobile.model.UserOperate;
import com.fx.mobile.user.constants.enums.UserStatusEnum;
import com.fx.mobile.user.dao.UserOperateDao;
import com.fx.mobile.user.mail.util.MailSenderInfo;
import com.fx.mobile.user.mail.util.SimpleMailSender;
import com.fx.mobile.user.service.ResourceProperties;
import com.fx.mobile.user.service.UserOperateService;
import com.fx.mobile.user.util.InterfaceClass;
import com.fx.mobile.user.util.MD5;
import com.fx.mobile.user.util.MailActivationCodeProcessor;
import com.fx.mobile.user.util.TokenProcessor;

@Scope("prototype")
@Service("operateService")

public class UserOperateServiceImp implements UserOperateService {
 
	public int updateUserPwd(String mobileNumber,String newPwd){

		return userOperateDao.updateUserPwd(mobileNumber, newPwd);
	}
	@Autowired
	public UserOperateDao userOperateDao;
	
	@Autowired
	private  ResourceProperties resourceProperties;
	
	public  DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	InterfaceClass interfaceClass = new InterfaceClass();
	 public Log log = LogFactory.getLog(getClass()); 
	@Override
	@SuppressWarnings("static-access")
	public String loginQQ(UserOperate user) {
		
		List<UserOperate> list = userOperateDao.longinQQ(user);
		if(list.size()>0){
			return UserStatusEnum.USER_LOGIN_SUC.getCode();//登录成功
		}
		return UserStatusEnum.USER_LOGIN_FAIL.getCode();//失败
	}
	@Override
	@SuppressWarnings("static-access")
	public String login(UserOperate user) {
		if(user.getUserPassword().length()<=19){
			user.setUserPassword(MD5.getMD5Passwored(user.getUserPassword()));//使用md5对密码加密
		}
		List<UserOperate> list = userOperateDao.longin(user);
		if(list.size()>0){
			return UserStatusEnum.USER_LOGIN_SUC.getCode();//登录成功
		}
		return UserStatusEnum.USER_LOGIN_FAIL.getCode();//失败
	}

	/**
	 * 注册用户
	 */
	@Override
	@SuppressWarnings("static-access")
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackForClassName = { "java.lang.Throwable" })
	public String registAccount(UserOperate user) throws Exception{
		try {
			//查询验证用户是否已存在
			if(!userOperateDao.isExistUser(user)){
				//有效期
				//String d = df.format(new Date());
				//Date date1 = df.parse(d);
				//Long dif = date1.getTime(); 
				//user.setExpireseIn(String.valueOf(dif));

				String s = userOperateDao.registAccount(user);//注册账户。

				if(s.equals(UserStatusEnum.USER_REGIT_SUC.getCode())){
//					/**调用运营系统 新增积分信息*/
//					String markeHttpUrl= UrlProperties.getMarketHttpUrl();
//	            	String path=markeHttpUrl+"/marketing/interfaces!registUserIntegrals.action?openId="+user.getOpenId();
//	            	String status = interfaceClass.getMarketIntegralJson(path);//调用运用系统中的接口方法
//					if("0".equals(status)){
//						log.info("注册用户积分成功");
//					}else{
//						log.info("注册用户积分失败");
//						throw new Exception();
//					}
					return UserStatusEnum.USER_REGIT_SUC.getCode();//注册成功
				}else{
					return UserStatusEnum.USER_REGIT_FAIL.getCode();//注册失败
				}
			}else{
				return UserStatusEnum.USER_EXISTED.getCode();//用户存在
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			throw e;
			//return "005";
		}
	}
	
	/**
	 * 根据用户输入的信息：Email、用户名或PhicommId判断此用户是否存在
	 */
	@Override
	public String findUsers(UserOperate user) {
		//try {
			List<UserOperate> list = userOperateDao.findUsers(user);
			if(list.size()>0){
				return UserStatusEnum.USER_EXISTED.getCode();//用户已存在
			}
			return UserStatusEnum.USER_NOT_EXISTED.getCode();//用户不存在
		/*} catch (Exception e) {
			System.out.println(e.getMessage());
			return "005";
		}*/
	}
	
	/**
	 * 用户注册后，需要查找用户注册的指令信息，返回给用户登录验证（这里返回用户对象）
	 */
	@Override
	public List<UserOperate> findRegistInfoBackClien(UserOperate user) {
		List<UserOperate> list = userOperateDao.findUsers(user);
		return list;
	}
	
	/**
	 * 修改用户登录验证信息(用户使用用户名和密码登录后需要的操作)
	 */
	@Override
	public String updateLoginToken(UserOperate user) {
		try {
			//重新生成用户访问令牌
			String token = TokenProcessor.getInstance().generateToken("lingpai",true); 
			user.setAccessToken(token);
			//有效期（当前时间）
			user.setExpireseIn(df.format(new Date()));
			userOperateDao.updateLoginInfo(user);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return UserStatusEnum.USER_UPDATETOKEN_FAIL.getCode();
		}
		return UserStatusEnum.USER_UPDATETOKEN_SUC.getCode();
	}
	@Override
	public String updateMailActivationCode(UserOperate user){
		try{
			String mailActivationCode=MailActivationCodeProcessor.getInstance().generateToken("mailcode", true);
			user.setMailActivationCode(mailActivationCode);
			user.setMailActivationCodeExpireseIn(df.format(new Date()));
		}catch (Exception e) {
			//return UserStatusEnum.USER_UPDATECODE_FAIL.getCode();
		}
		return null;
	}
	@Override
    public boolean isMailActivated(String mailoruser){
		boolean is=true;
		List<UserOperate> findByUserName=userOperateDao.findUserByUserName(mailoruser);
		List<UserOperate> findByMail=userOperateDao.findUserByUserMail(mailoruser);
		
    	if(findByUserName.isEmpty() && !findByMail.isEmpty()){
    		if(findByMail.get(0).getIsMailActivated()==0 || findByMail.get(0).getIsMailActivated()==null){
    			is=false;
    		}
    	}
    	if(findByMail.isEmpty() && !findByUserName.isEmpty()){
    		if(findByUserName.get(0).getIsMailActivated()==0 || findByUserName.get(0).getIsMailActivated()==null){
    			is=false;
    		}
    	}
    	return is;
    }
	/**
	 * 邮箱重置密码
	 */
	@Override
	public boolean resetMail(UserOperate user){
		 MailSenderInfo mailInfo = new MailSenderInfo();   
	      //mailInfo.setMailServerHost("smtp.163.com");   
	      mailInfo.setMailServerHost(resourceProperties.getMailserverhost()); 
	      mailInfo.setMailServerPort(resourceProperties.getMailserverport());   
	      mailInfo.setValidate(true);   
	      mailInfo.setUserName(resourceProperties.getUsername());   
	      mailInfo.setPassword(resourceProperties.getPassword());//您的邮箱密码   
	      mailInfo.setFromAddress(resourceProperties.fromaddress);   
	      mailInfo.setToAddress(user.getUserEmail());   
	      mailInfo.setSubject("找回密码");   
	      mailInfo.setContent("斐密码找回邮件邮件，请点击以下链接找回密码："
	      		+ "'http://"+resourceProperties.getContent()+"/mobile-user/generalunify/tofindpassreset?phicommId="+user.getPhicommId()+"&findPassCode="+user.getFindPassCode()+"'");   
	         //这个类主要来发送邮件  
	      SimpleMailSender sms = new SimpleMailSender();  
	          if(sms.sendTextMail(mailInfo)==true){
	        	  return  true;
	          }else{
	        	  return false;
	          }
	}
	
    /**
     * 邮箱注册
     */
    @Override
	public String mailRegister(UserOperate user){
        	
			try {
				//查询验证用户是否已存在
				if(!userOperateDao.isExistUserByUserName(user)){
				    if(!userOperateDao.isExistUserByMail(user)){

						boolean s = userOperateDao.regist(user);//注册账户。

						if(s==true){
							  MailSenderInfo mailInfo = new MailSenderInfo(); 
							  System.out.println("=="+resourceProperties.getMailserverhost()+"=="+resourceProperties.getMailserverport());
						      //mailInfo.setMailServerHost("smtp.163.com");   
							  mailInfo.setMailServerHost(resourceProperties.getMailserverhost()); 
						      mailInfo.setMailServerPort(resourceProperties.getMailserverport());   
						      mailInfo.setValidate(true);   
						      mailInfo.setUserName(resourceProperties.getUsername());   
						      mailInfo.setPassword(resourceProperties.getPassword());//您的邮箱密码   
						      mailInfo.setFromAddress(resourceProperties.getFromaddress());   
						      mailInfo.setToAddress(user.getUserEmail());   
						      mailInfo.setSubject("账号激活邮件");   
						      mailInfo.setContent("斐讯账号激活邮件，请点击以下链接激活："
						      		+ "'http://"+resourceProperties.getContent()+"/mobile-user/generalunify/aftermailsendurl?mail="+user.getUserEmail()+"&mailActivationCode="+user.getMailActivationCode()+"&mailActivationCodeExpire="+user.getMailActivationCodeExpireseIn()+"'");   
						         //这个类主要来发送邮件  
						      SimpleMailSender sms = new SimpleMailSender();  
						          if(sms.sendTextMail(mailInfo)==true){
						        	  return  UserStatusEnum.USER_SENDMAIL_SUC.getCode();
						          }else{
						        	  return UserStatusEnum.USER_SENDMAIL_FAIL.getCode();
						          }
							
						}else{
							return UserStatusEnum.USER_REGISTERMAIL_FAIL.getCode();//注册失败
						}
				    }else{
				    	return UserStatusEnum.USER_REGISTERMAIL_USERMAILEXIT.getCode();//邮箱存在
				    }
				}
				else{
					return UserStatusEnum.USER_REGISTERMAIL_USERNAMEEXIT.getCode();//用户名存在
				}
			} catch (Exception e) {
				log.info(e.getMessage());
				throw e;
				//return "005";
			}

	}
	@Override
	public List<UserOperate> getSempUser(UserOperate userOperate) {
		return userOperateDao.getSempUser(userOperate);
	}
	//根据openId查询用户信息
	@Override
	public UserOperate findUserByOpenId(String openId) {
		UserOperate uo  = new UserOperate();
		List<UserOperate> list =userOperateDao.findUserByOpenId(openId);
		if(list.size()>0){
			uo = list.get(0);
		}else{
			uo = null;
			return uo;
		}
		return uo;
	}
	@Override
	//用户头像修改上传
	public void updateUserHeadPortraitByOpenid(String openid,String headPortraitPic) {
		
		userOperateDao.updateUserHeadPortraitByOpenid(openid, headPortraitPic);
		
	}
	@Override
	public void updateUserInfo(UserOperate userOp) {
		
		userOperateDao.updateUserInfo(userOp);
		
	}

	@Override
	public boolean booleanQQLogin(String openqqId) {
		boolean isloginok=true;
		if(userOperateDao.finduserByOpenqq(openqqId).isEmpty()){
	      
			isloginok=false;
		}
		// TODO Auto-generated method stub
		return isloginok;
	}
	@Override
	public boolean booleanWeixinLogin(String openweixin){
		boolean isloginok=true;
		if(userOperateDao.finduserByOpenweixin(openweixin).isEmpty()){
	      
			isloginok=false;
		}
		// TODO Auto-generated method stub
		return isloginok;
	};
	@Override
	public boolean booleanUserIsExist(String name) {
		// TODO Auto-generated method stub
		boolean isExist=true;
		if(userOperateDao.findUserByName(name).isEmpty()){
			isExist=false;
		}
		return isExist;
	}

	@Override
	public void updateQQInfoByUserNameOrMailOrPhoneNumber(UserOperate userOp) {
		// TODO Auto-generated method stub
		userOperateDao.updateQQInfoByUserNameOrMailOrPhoneNumber(userOp);
	}

	@Override
	public List<UserOperate> finduserByOpenqq(String openqqId) {
		// TODO Auto-generated method stub
		return userOperateDao.finduserByOpenqq(openqqId);
	}
	@Override
	public boolean updateUserOperateByKey(String key, String value,String phicomm_id) {
		// TODO Auto-generated method stub
		boolean istrue=userOperateDao.updateUserOperateByKey(key, value,phicomm_id);
		log.debug("start updateByKey----------------------------------------");
		if(istrue==true){
			return true;
		}else{
			return false;
		}
		
	}
	@Override
	public boolean updateUserOperateByKey2(String key1, String value1,
			String key2, String value2, String phicomm_id) {
		// TODO Auto-generated method stub
		boolean istrue=userOperateDao.updateUserOperateByKey2(key1, value1,key2,value2,phicomm_id);
		log.debug("start updateByKey2----------------------------------------");
		if(istrue==true){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public UserOperate findUserByPhicommId(String phicomm_id) {
		// TODO Auto-generated method stub

		log.debug("start findUserByPhicommId----------------------------------------");
 		return userOperateDao.findUserByPhicommId(phicomm_id).get(0);
	}


}
