package com.fx.mobile.user.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.fx.base.dao.imp.BaseDaoImpl;
import com.fx.mobile.model.UserOperate;
import com.fx.mobile.user.constants.enums.UserStatusEnum;
import com.fx.mobile.user.dao.UserOperateDao;
import com.fx.mobile.user.util.MD5;
import com.fx.mobile.user.util.TokenProcessor;

public class UserOperateDaoImp extends HibernateDaoSupport implements UserOperateDao {
	@Override
	public int updateUserPwd(String mobileNumber,String newPwd){
		
		String hql ="update UserOperate u set u.userPassword=? where u.userPhoneNumb=?";
		int count = this.getHibernateTemplate().bulkUpdate(hql, new Object[]{newPwd,mobileNumber});
		
		return count ;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<UserOperate> longinQQ(UserOperate user) {
		String hql ="";
		List<UserOperate> list = null;
	   // user.getUserPassword()=MD5.getMD5Passwored(user);
		// TODO Auto-generated method stub
		if(user.getOpenqq()==null){
			return new ArrayList();
		}
		    
	    hql = "from UserOperate where openqq=?  and userPassword=?";
	    list = this.getHibernateTemplate().find(hql, user.getOpenqq(),user.getUserPassword());
	
		/*if(user.getUserPhoneNumb()!=null){//根据用户手机号登录
			hql = "from User where userPhoneNumb=? and userPassword=?";
			list = this.getHibernateTemplate().find(hql,user.getUserPhoneNumb(),user.getUserPassword());
		}*/
		/*if(user.getUserEmail()!=null){//根据用户邮箱登录
			hql = "from User where userEmail=? and userPassword=?";
			list = this.getHibernateTemplate().find(hql,user.getUserEmail(),user.getUserPassword());
		}*/
		/*if(user.getPhicommId()!=null){//根据用户邮箱登录
			hql = "from UserOperate where phicommId=? and userPassword=?";
			list = this.getHibernateTemplate().find(hql,user.getPhicommId().toString(),user.getUserPassword());
		}*/
		// TODO Auto-generated method stub
		return list;
	}
	//用户登录查询是否存在登录
	@Override
	@SuppressWarnings("unchecked")
	public List<UserOperate> longin(UserOperate user) {
		String hql ="";
		List<UserOperate> list = null;
	   // user.getUserPassword()=MD5.getMD5Passwored(user);
		// TODO Auto-generated method stub
		if(user.getUserPhoneNumb()==null || user.getUserPhoneNumb().equals("") || user.getUserPassword()==null || user.getUserPassword().equals("")){
			return new ArrayList();
		}
		    
	    hql = "from UserOperate where (userPhoneNumb=? or userName=?  or userEmail=?)  and userPassword=?";
	    list = this.getHibernateTemplate().find(hql, user.getUserPhoneNumb(),user.getUserPhoneNumb(),user.getUserPhoneNumb(),user.getUserPassword());
	
		/*if(user.getUserPhoneNumb()!=null){//根据用户手机号登录
			hql = "from User where userPhoneNumb=? and userPassword=?";
			list = this.getHibernateTemplate().find(hql,user.getUserPhoneNumb(),user.getUserPassword());
		}*/
		/*if(user.getUserEmail()!=null){//根据用户邮箱登录
			hql = "from User where userEmail=? and userPassword=?";
			list = this.getHibernateTemplate().find(hql,user.getUserEmail(),user.getUserPassword());
		}*/
		/*if(user.getPhicommId()!=null){//根据用户邮箱登录
			hql = "from UserOperate where phicommId=? and userPassword=?";
			list = this.getHibernateTemplate().find(hql,user.getPhicommId().toString(),user.getUserPassword());
		}*/
		// TODO Auto-generated method stub
		return list;
	}
    //注册
	@Override
	@SuppressWarnings("unchecked")
	public boolean regist(UserOperate user) {
		try {
		this.getHibernateTemplate().save(user);
		}catch(Exception e){
			return false;
		}
		return true;
	}
	//用户注册
	@Override
	@SuppressWarnings("unchecked")
	public String registAccount(UserOperate user) {
		try {
			//List<UserOperate> list = null;
			//String hql ="";
			if(user.getUserName()!=null){
				//hql ="insert into UserOperate(userName,userPassword,openId,openKey,accessToken,expireseIn) values(?,?,?,?,?,?)";
				this.getHibernateTemplate().save(user);
			}
			if(user.getUserPhoneNumb()!=null){
				//hql ="insert into UserOperate(userPhoneNumb,userPassword,openId,openKey,accessToken,expireseIn) values(?,?,?,?,?,?)";
				this.getHibernateTemplate().save(user);
			}
			if(user.getUserEmail()!=null){
				//hql ="insert into UserOperate(userPhoneNumb,userPassword,openId,openKey,accessToken,expireseIn) values(?,?,?,?,?,?)";
				this.getHibernateTemplate().save(user);
			}
			if(user.getOpenqq()!=null){
				//hql ="insert into UserOperate(userPhoneNumb,userPassword,openId,openKey,accessToken,expireseIn) values(?,?,?,?,?,?)";
				this.getHibernateTemplate().save(user);
			}
			/*if(user.getUserEmail()!=null){
				//hql ="insert into UserOperate(userEmail,userPassword,openId,openKey,accessToken,expireseIn) values(?,?,?,?,?,?,?)";
				this.getHibernateTemplate().save(user);
				// new String[]{user.getUserEmail(),user.getUserPassword(),user.getOpenId(),user.getOpenKey(),user.getAccessToken(),user.getExpireseIn()}
			}*/
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return UserStatusEnum.USER_REGIT_EXCEPTION.getCode();
			}
		return UserStatusEnum.USER_REGIT_SUC.getCode();
	}

	//查询单个用户是否存在
	@Override
	@SuppressWarnings("unchecked")
	public List<UserOperate> findUsers(UserOperate user) {
		// TODO Auto-generated method stub
               if(user.getUserPhoneNumb()==null || user.getUserPhoneNumb().equals("")){
            	   return new ArrayList();
               }
				String hql="from UserOperate where userPhoneNumb=? or userName=?  or userEmail=?";
				List<UserOperate> list = this.getHibernateTemplate().find(hql,user.getUserPhoneNumb(),user.getUserPhoneNumb(),user.getUserPhoneNumb());
				return list;
	}
	
	//查询单个用户是否存在(邮箱)
	@Override
	@SuppressWarnings("unchecked")
	public List<UserOperate> findUsersByMail(UserOperate user) {
		// TODO Auto-generated method stub
				String hql="from UserOperate where userEmail=?";
				List<UserOperate> list = this.getHibernateTemplate().find(hql,user.getUserEmail());
				return list;
	}
	//查询单个用户是否存在(邮箱)
	@Override
	@SuppressWarnings("unchecked")
	public List<UserOperate> findUsersByPhone(UserOperate user) {
		// TODO Auto-generated method stub
				String hql="from UserOperate where userPhoneNumb=?";
				List<UserOperate> list = this.getHibernateTemplate().find(hql,user.getUserPhoneNumb());
				return list;
	}
		//查询单个用户是否存在(用户名)
    @Override
    @SuppressWarnings("unchecked")
	public List<UserOperate> findUsersByUserName(UserOperate user) {
				// TODO Auto-generated method stub
						String hql="from UserOperate where userName=?";
						List<UserOperate> list = this.getHibernateTemplate().find(hql,user.getUserName());
						return list;
			}
    //查询openqqid是否已经存在

	@SuppressWarnings("unchecked")
	@Override
	public List<UserOperate> findEndPhicommId() {
		// TODO Auto-generated method stubselect * from user_operate order by  id desc  limit 1
		String hql ="from UserOperate order by id desc limit 0,1";
		List<UserOperate> list = this.getHibernateTemplate().find(hql);
		return list;
	}
	@Override
	public void updateLoginInfo(UserOperate user) {
		//String hql = "update UserOperate set accessToken=?,expireseIn=? where id=?";
		
		this.getHibernateTemplate().update(user);
		
		
	}
	
	@SuppressWarnings("unchecked")
	public List<UserOperate> getSempUser(UserOperate userOperate){
		
		String hql ="from UserOperate where accessToken='"+userOperate.getAccessToken()+"' and openId='"+userOperate.getOpenId()+"'";
		List<UserOperate> list = this.getHibernateTemplate().find(hql);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<UserOperate> findUserByOpenId(String openId) {
		String hql = "from UserOperate where openId='"+openId+"'";
		List<UserOperate> list = this.getHibernateTemplate().find(hql);
		return list;
	}
	@Override
	public void updateUserHeadPortraitByOpenid(String openid,String headPortraitPic) {
		
		String SQL = "update user_operate set figureurl='"+headPortraitPic+"' where open_id='"+openid+"'";
		this.getSession().createSQLQuery(SQL).executeUpdate();
		this.getSession().close();
		
	}
	@Override
	public void updateQQInfoByUserNameOrMailOrPhoneNumber(UserOperate userOperate){
	    String SQL="update user_operate set openqq='"+userOperate.getOpenqq()+"',openqqinfo='"+userOperate.getOpenqqinfo()+"' where userName='"+userOperate.getUserPhoneNumb()+"' or user_PhoneNumb='"+userOperate.getUserPhoneNumb()+"' or userEmail='"+userOperate.getUserPhoneNumb()+"'";
	    this.getSession().createSQLQuery(SQL).executeUpdate();
		this.getSession().close();
	}
	@Override
	public void update(UserOperate user){
		
		this.getHibernateTemplate().update(user);
	}
	@Override
	public void updateUserInfo(UserOperate userOp) {
		try {
			 Map<String,Object> map = new HashMap<String,Object>(); 
			 StringBuffer sb = new StringBuffer("update user_operate set ");
			 if(userOp.getOpenId()!= null){
				 sb.append("open_id =:open_id ");
				 
			 }
			 if(userOp.getUserName()!= null){
				 sb.append(",userName= :userName ");
				   map.put("userName",userOp.getUserName());  
			 }
			 if(userOp.getNickname()!= null){
				 sb.append(",nickname= :nickname ");
				   map.put("nickname",userOp.getNickname());  
			 }
			 if(userOp.getBirthday()!= null){
				 sb.append(",birthday = :birthday ");
				 map.put("birthday",userOp.getBirthday());  
			 }
			 if(userOp.getCity()!= null){
				 sb.append(",city = :city ");
				 map.put("city",userOp.getCity());  
			 }
			 if(userOp.getProvince()!= null){
				 sb.append(",province = :province ");
				 map.put("province",userOp.getProvince());  
			 }
			 if(userOp.getSex()!= null){
				 sb.append(",sex = :sex ");
				 map.put("sex",userOp.getSex());  
			 }
			 sb.append("  where open_id=:open_id");
			 map.put("open_id",userOp.getOpenId());  
			//String hql ="update UserOperate set userName=?,nickname=?,birthday=?,sex=?,province=?,city=? where id=?";
			//this.getHibernateTemplate().load(UserOperate.class, id);
			 SQLQuery sqlquery = this.getSession().createSQLQuery(sb.toString());
			 sqlquery.setProperties(map); 
			 sqlquery.executeUpdate();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
	}
	@Override
	public boolean isExistUser(UserOperate user) {
		List<UserOperate> userOperList = findUsers(user);
		return userOperList.size()>0 ? true : false;
	}
	@Override
    public boolean isExistUserByUserName(UserOperate user){
    	List<UserOperate> userOperList = findUsersByUserName(user);
    	return userOperList.size()>0 ? true : false;
    }
    @Override
    public boolean isExistUserByMail(UserOperate user){
    	List<UserOperate> userOperList = findUsersByMail(user);
    	return userOperList.size()>0 ? true : false;
    }
    @Override
	public List<UserOperate> finduserByOpenqq(String openqq) {
		// TODO Auto-generated method stub
		if(openqq==null || openqq.equals("")){
			return new ArrayList();
		}
		String hql = "from UserOperate where openqq='"+openqq+"'";
		List<UserOperate> list = this.getHibernateTemplate().find(hql);
		return list;
	}
    @Override
  	public List<UserOperate> finduserByOpenweixin(String openweixin) {
  		// TODO Auto-generated method stub
  		if(openweixin==null || openweixin.equals("")){
  			return new ArrayList();
  		}
  		String hql = "from UserOperate where openweixin='"+openweixin+"'";
  		List<UserOperate> list = this.getHibernateTemplate().find(hql);
  		return list;
  	}
	@Override
	public List<UserOperate> findUserByName(String name) {
		// TODO Auto-generated method stub
		if(name==null || name.equals("")){
			
			return new ArrayList();
		}
		String hql="from UserOperate where userPhoneNumb=? or userName=?  or userEmail=?";
		List<UserOperate> list = this.getHibernateTemplate().find(hql,name,name,name);
		return list;
	}

	@Override
	public List<UserOperate> findUserByNameAndPass(String name, String pass) {
		String hql ="";
		List<UserOperate> list = null;
		pass=MD5.getMD5Passwored(pass);
		// TODO Auto-generated method stub
		if(name==null || name.equals("") || pass==null || pass.equals("")){
			return new ArrayList();
		}
		    
	    hql = "from UserOperate where (userPhoneNumb=? or userName=?  or userEmail=?)  and userPassword=?";
	    list = this.getHibernateTemplate().find(hql, name,name,name,pass);
		return list;
	}
	@Override
	public boolean updateUserOperateIsMailActivated(Long is,String mail){
		 String SQL="update user_operate set isMailActivated="+is+"  where userEmail='"+mail+"'";
		 try{
		        this.getSession().createSQLQuery(SQL).executeUpdate();
		        return true;
		    
		    }catch(Exception e){
		    	return false;
		    }finally{
			this.getSession().close();}
	}
	/**
	 * 单个字段更新useroperate
	 */
	@Override
	public boolean updateUserOperateByKey(String key, String value,String phicomm_id) {
		 String SQL="update user_operate set "+key+"= '"+value+"' where phicomm_id= "+phicomm_id+" ";
		    try{
		        this.getSession().createSQLQuery(SQL).executeUpdate();
		        return true;
		    
		    }catch(Exception e){
		    	return false;
		    }finally{
			this.getSession().close();}
	}
	@Override
	public boolean updateUserOperateByKey2(String key1, String value1,
			String key2, String value2, String phicomm_id) {
		// TODO Auto-generated method stub
		String SQL="update user_operate set "+key1+"= '"+value1+"' ,"+key2+" ='"+value2+" 'where phicomm_id= "+phicomm_id+" ";
	    try{
	        this.getSession().createSQLQuery(SQL).executeUpdate();
	        return true;
	    
	    }catch(Exception e){
	    	return false;
	    }finally{
		this.getSession().close();}
	}
	@Override
	public List<UserOperate> findUserByPhicommId(String phicomm_id) {
		// TODO Auto-generated method stub
		String hql ="";
		List<UserOperate> list = null;
		hql = "from UserOperate where phicommId=?";
	    list = this.getHibernateTemplate().find(hql, phicomm_id);
		return list;
	}
	@Override
	public List<UserOperate> findUserByUserName(String username) {
		// TODO Auto-generated method stub
		String hql ="";
		List<UserOperate> list = null;
		hql = "from UserOperate where userName=?";
	    list = this.getHibernateTemplate().find(hql, username);
		return list;
	}
	@Override
	public List<UserOperate> findUserByUserMail(String mail) {
		// TODO Auto-generated method stub
		String hql ="";
		List<UserOperate> list = null;
		hql = "from UserOperate where userEmail=?";
	    list = this.getHibernateTemplate().find(hql, mail);
		return list;
	}


  
}
