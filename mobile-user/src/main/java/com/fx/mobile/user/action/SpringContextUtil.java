package com.fx.mobile.user.action;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;



public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

   // @Override
    public void setApplicationContext(ApplicationContext applicationContext)  {
    	System.out.println("***********");
        SpringContextUtil.applicationContext = applicationContext;
        System.out.println(applicationContext);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }
   /* public static void main(String[] args) {
		UserOperateServiceImp imp= new UserOperateServiceImp();
		UserOperate user =new UserOperate();
		user.setUserPhoneNumb("14521542256");
		user.setUserPassword("000000");
		imp.registAccount(user);
	}*/
}
