package ar.com.educacionit.services.impl;
import java.util.List;

import ar.com.educacionit.domain.Role;
import ar.com.educacionit.domain.User;
import ar.com.educacionit.exceptions.GenericException;
import ar.com.educacionit.exceptions.ServiceException;
import ar.com.educacionit.repository.repository.RoleRepository;
import ar.com.educacionit.repository.repository.UserRepository;
import ar.com.educacionit.repository.repository.impl.RoleRepositoryImpl;
import ar.com.educacionit.repository.repository.impl.UserRepositoryImpl;
import ar.com.educacionit.services.UserService;

public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private RoleRepository rr;
	
	public UserServiceImpl() {
		this.userRepository = new UserRepositoryImpl();
		this.rr = new RoleRepositoryImpl();
	}
	
	@Override
	public User getUserByUsername(String username) throws ServiceException {
		
		try {
			return this.userRepository.getUser(username);
		}catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<Role> findRoles() throws ServiceException {
		try {
			return this.rr.findRoles();
		} catch (GenericException e) {
			throw new ServiceException("No se ha podido obtener la lista de tipo de productos", e);
		}
	}
}
