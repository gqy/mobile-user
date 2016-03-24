package com.fx.mobile.user.service;

import java.util.List;

import com.fx.mobile.model.NoteNumbRecord;
import com.fx.mobile.model.UserOperate;

public interface UserinterfaceService {
	
	//------------提供给外部项目接口
	/**
	 * 根据openId查找用户信息
	 */
	public List<UserOperate> findUserOperateByOpenId(String openId);
	
	/**
	 * 根据openid 修改用户信息
	 * @param openId
	 * @param integarl
	 */
	public void updateUserOperateIntegral(String openId,Float integarl);
	
	/**
	 * 查询短信数
	 * @return
	 */
	public List<NoteNumbRecord> findNoteNumb();

}
