package com.fx.mobile.hessian;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;







public class StartupListener extends ContextLoaderListener implements ServletContextListener {

	protected Log log = LogFactory.getLog(getClass());

    boolean active;

	
	Properties props;
 
	@Override
	public void contextInitialized(ServletContextEvent event) {
		if (log.isDebugEnabled()) {
			log.debug("initializing context...");
		}
		super.contextInitialized(event);
		loadProperties(); 
		ServletContext servletContext = event.getServletContext();
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		WebConfig.setContext(context);
		String appPath = servletContext.getRealPath("");
		WebConfig.setAppPath(appPath);
		if (log.isDebugEnabled()) {
			log.debug("appPath"+appPath);
		}
		//set templates path
		String sep = java.io.File.separator;
		WebConfig.setTemplatesPath(appPath + sep + "WEB-INF" + sep + "templates" + sep);
		WebConfig.setWebisStarted(true);
		WebConfig.setStaticPath("");

	}

	private void loadProperties() {
		ClassLoader loader=Thread.currentThread().getContextClassLoader(); 
		URL url=loader.getResource("jdbc.properties"); 
		InputStreamReader  read =  null;
		BufferedReader reader= null;
		try { 
			try {
				read = new InputStreamReader (url.openStream(),"UTF-8");
				reader=new BufferedReader(read);
				props = new Properties(); 
				props.load(reader);
			} finally{
				reader.close();
			    read .close();
			}
        } catch (IOException e) { 
        	log.error(e,e);
        	throw new RuntimeException("读取配置错误");
        }
	}
	

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		WebConfig.setWebisStarted(false);
		active=false;
		super.contextDestroyed(event);
		
	}



}
