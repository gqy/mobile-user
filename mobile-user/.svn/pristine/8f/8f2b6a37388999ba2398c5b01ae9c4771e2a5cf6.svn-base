package com.fx.mobile.junit;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fx.mobile.model.UserOperate;
import com.fx.mobile.user.dao.UserOperateDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:context/*-context.xml"}) 
public class UserOperateDaoImplTest {
	 @Resource UserOperateDao userOperateDao;
	 @Test
	 public void getListTest(){
	     List<UserOperate> userList = userOperateDao.findUserByName("芳草碧连天") ;
	     System.out.println("1---------------------------------------------------------------------------------------------------------------------------------------"+userList.toString());
	    }
//	 @Test
//	 public void getUserByNameAndPass(){
//		 List<UserOperate> userList = userOperateDao.findUserByNameAndPass("芳草碧连天", "");
//		 System.out.println("2---------------------------------------------------------------------------------------------------------------------------------------"+userList.toString());
//	 }
}
