package ar.com.educacionit.repository.repository;

import ar.com.educacionit.domain.User;
import ar.com.educacionit.exceptions.GenericException;

public interface UserRepository {

	User getUser(String username) throws GenericException;

}
