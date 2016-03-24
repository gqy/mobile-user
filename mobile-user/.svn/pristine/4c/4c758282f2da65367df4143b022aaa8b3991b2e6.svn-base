package com.fx.mobile.user.service;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.fx.base.dao.imp.BaseDaoImpl;

public abstract class CommonDao extends BaseDaoImpl{
	
	
	
	public CommonDao(final Class persistentClass)
	{
		super(persistentClass);
	}
	
	public int sqlSaveOrUpdate(String sql) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Transaction tr = session.beginTransaction();
		tr.begin();
		int result = 0;
		result = session.createSQLQuery(sql).executeUpdate();
		tr.commit();
		return result;
	}

    public String hqlSaveOrUpdate(String hql) {
    	this.getHibernateTemplate().saveOrUpdate(hql, null);
    	return null;
	}
    
}
