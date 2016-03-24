package com.fx.mobile.user.service;

import java.util.List;

import com.fx.mobile.model.NoteNumbRecord;

public interface NoteNumRecordService {
	
	/**
	 * 查询记录数
	 * @return
	 */
	public List<NoteNumbRecord> findNoteNumb();
	
	/**
	 * 更新短信总记录条数
	 * @return
	 */
	public void updateNoteNumb(NoteNumbRecord nodeNumbRecord);
	
	/**
	 * 查询和更新方法执行事务
	 * @throws Exception
	 */
	public String executeMethod() throws Exception;

}
