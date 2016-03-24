package com.fx.mobile.hessian;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;




public class WebConfig {
	
	private static final long serialVersionUID = 1L;
	static Log log = LogFactory.getLog(WebConfig.class);
	
	private  static   WebApplicationContext context;

	private static String templatesPath; //full path to templates directory where formatted report templates and mondiran cube definitions are stored
	private static String relativeTemplatesPath; //relative path to templates directory. used by showAnalysis.jsp
	private static String appPath; //application path. to be used to get/build file paths in non-servlet classes
	private static org.quartz.Scheduler scheduler; //to allow access to scheduler from non-servlet classes

	
	private static String staticPath ;
	
	private static boolean webisStarted = false; 
	


	public static WebApplicationContext getContext() {
		return context;
	}

	public static void setContext(WebApplicationContext context) {
		WebConfig.context = context;
	}
	  
	  
	public static Object getBean(String name) {
		return context.getBean(name);
	}




	public static String getTemplatesPath() {
		return templatesPath;
	}

	public static void setTemplatesPath(String templatesPath) {
		WebConfig.templatesPath = templatesPath;
	}

	public static String getRelativeTemplatesPath() {
		return relativeTemplatesPath;
	}

	public static void setRelativeTemplatesPath(String relativeTemplatesPath) {
		WebConfig.relativeTemplatesPath = relativeTemplatesPath;
	}

	public static String getAppPath() {
		return appPath;
	}

	public static void setAppPath(String appPath) {
		WebConfig.appPath = appPath;
	}

	public static org.quartz.Scheduler getScheduler() {
		return scheduler;
	}

	public static void setScheduler(org.quartz.Scheduler scheduler) {
		WebConfig.scheduler = scheduler;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public static String getStaticPath() {
		return staticPath;
	}

	public static void setStaticPath(String staticPath) {
		WebConfig.staticPath = staticPath;
	}

	public static boolean isWebisStarted() {
		return webisStarted;
	}

	public static void setWebisStarted(boolean webisStarted) {
		WebConfig.webisStarted = webisStarted;
	}


}
