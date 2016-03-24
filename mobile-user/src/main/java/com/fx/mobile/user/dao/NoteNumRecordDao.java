package com.fx.mobile.user.dao;

import java.util.List;

import com.fx.mobile.model.NoteNumbRecord;


public interface NoteNumRecordDao {
	
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

}
