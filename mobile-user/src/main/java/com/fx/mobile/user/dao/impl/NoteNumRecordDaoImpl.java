package com.fx.mobile.user.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.fx.mobile.model.NoteNumbRecord;
import com.fx.mobile.user.dao.NoteNumRecordDao;

public class NoteNumRecordDaoImpl extends HibernateDaoSupport implements
		NoteNumRecordDao {

	@SuppressWarnings("unchecked")
	public List<NoteNumbRecord> findNoteNumb() {
		List<NoteNumbRecord> list = null;
		String hql = "from NoteNumbRecord";
		list = this.getHibernateTemplate().find(hql);
		return list;
	}

	@SuppressWarnings("null")
	public void updateNoteNumb(NoteNumbRecord nodeNumbRecord) {
		//if (nodeNumbRecord == null) {// 如果等于空就说明不存在数据记录，添加一条记录
		//	NoteNumbRecord numbRecord = new NoteNumbRecord();
		//	numbRecord.setNodeNumb(1L);
			//String hql = "insert into NoteNumbRecord(nodeNumb) value(?)";
			//this.getHibernateTemplate().save(hql, numbRecord);
		//	this.getHibernateTemplate().save(numbRecord);
		//} else {// 如果存在就修改记录
			Long countNodeNumb = nodeNumbRecord.getNodeNumb() + 1;
			nodeNumbRecord.setNodeNumb(countNodeNumb);
			this.getHibernateTemplate().update(nodeNumbRecord);
		//}
	}

}
