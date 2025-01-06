package ar.com.educacionit.repository.repository;

import org.hibernate.SessionFactory;

import ar.com.educacionit.hibernate.HibernateUtils;

public abstract class HibernateBaseRepository {

	protected SessionFactory factory;
	
	public HibernateBaseRepository() {
		this.factory = HibernateUtils.getSessionFactory();
	}
}
