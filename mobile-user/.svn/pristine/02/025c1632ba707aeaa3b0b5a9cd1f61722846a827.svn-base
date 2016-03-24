package com.fx.mobile.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fx.mobile.model.NoteNumbRecord;
import com.fx.mobile.model.UserOperate;
import com.fx.mobile.user.dao.UserInterfaceDao;
import com.fx.mobile.user.service.UserinterfaceService;

@Scope("prototype")
@Service("userInterfaceService")
public class UserInterfaceServiceImpl implements UserinterfaceService {

	
	@Autowired
	public UserInterfaceDao userInterfaceDao;
	
	public List<UserOperate> findUserOperateByOpenId(String openId) {
		
		return userInterfaceDao.findUserOperateByOpenId(openId);
	}

	public void updateUserOperateIntegral(String openId, Float integarl) {

		userInterfaceDao.updateUserOperateIntegral(openId, integarl);
	}

	public List<NoteNumbRecord> findNoteNumb() {
		
		return userInterfaceDao.findNoteNumb();
	}

}
