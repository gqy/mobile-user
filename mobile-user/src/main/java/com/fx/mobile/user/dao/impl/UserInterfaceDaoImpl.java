package com.fx.mobile.user.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.fx.mobile.model.NoteNumbRecord;
import com.fx.mobile.model.UserOperate;
import com.fx.mobile.user.dao.UserInterfaceDao;

public class UserInterfaceDaoImpl extends HibernateDaoSupport implements UserInterfaceDao{

	@SuppressWarnings("unchecked")
	public List<UserOperate> findUserOperateByOpenId(String openId) {
		String hql = "from UserOperate where openId='"+openId+"'";
		List<UserOperate> list = this.getHibernateTemplate().find(hql);
		return list;
	}

	public void updateUserOperateIntegral(String openId, Float integarl) {
		String hql ="update UserOperate set integral="+integarl+" where openId="+openId;
		this.getSession().createSQLQuery(hql).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<NoteNumbRecord> findNoteNumb() {
		List<NoteNumbRecord> list = null;
		String hql = "from NoteNumbRecord";
		list = this.getHibernateTemplate().find(hql);
		return list;
	}

}
