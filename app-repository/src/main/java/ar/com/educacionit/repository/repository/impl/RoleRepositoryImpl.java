package ar.com.educacionit.repository.repository.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import ar.com.educacionit.domain.Role;
import ar.com.educacionit.exceptions.GenericException;
import ar.com.educacionit.repository.repository.HibernateBaseRepository;
import ar.com.educacionit.repository.repository.RoleRepository;

public class RoleRepositoryImpl extends HibernateBaseRepository implements RoleRepository {

	public RoleRepositoryImpl() {
		super();
	}
	
	@Override
	public List<Role> findRoles() throws GenericException{
		
		List<Role> roles = null;
		
		Session session = super.factory.getCurrentSession();
		
		try {
			session.getTransaction().begin();
			
			String sql = "Select r from " + Role.class.getName() + " r";
			
			Query<Role> query = session.createQuery(sql, Role.class);
					
			roles = query.getResultList();
			
			session.getTransaction().commit();
		}catch (Exception e) {
			session.getTransaction().rollback();
			throw new GenericException(e.getMessage(), e);
		}
		
		return roles;
	}

}
