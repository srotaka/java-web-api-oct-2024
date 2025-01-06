package ar.com.educacionit.services;

import java.util.List;

import ar.com.educacionit.domain.Role;
import ar.com.educacionit.domain.User;
import ar.com.educacionit.exceptions.ServiceException;

public interface UserService {

	public User getUserByUsername(String username) throws ServiceException;
	
	public List<Role> findRoles() throws ServiceException;
}
