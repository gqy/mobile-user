package com.fx.mobile.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fx.mobile.model.CrashLog;
import com.fx.mobile.user.dao.CrashLogDao;
import com.fx.mobile.user.service.CrashLogService;

@Scope("prototype")
@Service("crashLogService")
public class CrashLogServiceImpl implements CrashLogService {

	@Autowired
	public CrashLogDao crashLogDao;
	
	public void addCrashLog(CrashLog crashLog) {

		crashLogDao.addCrashLog(crashLog);
	}

}
