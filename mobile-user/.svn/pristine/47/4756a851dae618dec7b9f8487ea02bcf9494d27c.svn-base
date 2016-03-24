package com.fx.mobile.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fx.mobile.model.NoteNumbRecord;
import com.fx.mobile.user.dao.NoteNumRecordDao;
import com.fx.mobile.user.service.NoteNumRecordService;

@Scope("prototype")
@Service("noteNumRecordService")
public class NoteNumRecordServiceImpl implements NoteNumRecordService {

	@Autowired
	public NoteNumRecordDao noteNumRecordDao;
	
	public List<NoteNumbRecord> findNoteNumb() {

		return noteNumRecordDao.findNoteNumb();
	}

	public void updateNoteNumb(NoteNumbRecord nodeNumbRecord) {

		noteNumRecordDao.updateNoteNumb(nodeNumbRecord);
	}
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackForClassName = { "java.lang.Throwable" })
	public String executeMethod() throws Exception {
		String result ="0";
		try {
			List<NoteNumbRecord> list = noteNumRecordDao.findNoteNumb();
			
			if(list.size()>0){
				noteNumRecordDao.updateNoteNumb(list.get(0));
			}
			
			//Long numb =list.get(0).getNodeNumb();
			/*NoteNumbRecord noteNumbRecord =null;
			for(NoteNumbRecord nodeNumbRecords:list){
				noteNumbRecord=nodeNumbRecords;
			}*/
			//noteNumRecordDao.updateNoteNumb(noteNumbRecord);
			result = "1";
		} catch (Exception e) {
			result="0";
			e.printStackTrace();
			//throw e;
		}
		return result;
	}

}
