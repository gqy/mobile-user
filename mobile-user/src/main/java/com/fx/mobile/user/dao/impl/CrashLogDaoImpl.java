package com.fx.mobile.user.dao.impl;

import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.fx.mobile.model.CrashLog;
import com.fx.mobile.user.dao.CrashLogDao;

public class CrashLogDaoImpl extends HibernateDaoSupport implements CrashLogDao {

	public void addCrashLog(CrashLog crashLog) {
		
		LogFactory log;
		
		this.getHibernateTemplate().save(crashLog);
	}

}
