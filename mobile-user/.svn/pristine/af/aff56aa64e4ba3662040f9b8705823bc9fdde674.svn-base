package com.fx.mobile.user.action;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.fx.mobile.model.NoteNumbRecord;
import com.fx.mobile.user.service.NoteNumRecordService;


@Controller("notenumrecordAction")

@SuppressWarnings("serial")
public class NotenumrecordAction extends BaseAction {
	
	@Autowired
	public NoteNumRecordService noteNumRecordService;
	
	public String test(){
		List<NoteNumbRecord> list = noteNumRecordService.findNoteNumb();
		return null;
	}

}
