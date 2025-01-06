package ar.com.educacionit.repository.repository;

import java.util.List;

import ar.com.educacionit.domain.Role;
import ar.com.educacionit.exceptions.GenericException;

public interface RoleRepository {

	List<Role> findRoles() throws GenericException;

}
