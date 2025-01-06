package ar.com.educacionit.beans;

import java.util.stream.Collectors;

import ar.com.educacionit.beans.enums.PagesEnums;
import ar.com.educacionit.domain.User;
import ar.com.educacionit.exceptions.ServiceException;
import ar.com.educacionit.services.UserService;
import ar.com.educacionit.services.impl.UserServiceImpl;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class LoginBean {

	private String username;
	private String password;//carga en xhtml
	private String error;
		
	private UserService userService = new UserServiceImpl();
	
	@Inject
	private UsuarioBean usuarioBean;
	
	public String login() {
		
		PagesEnums target = PagesEnums.LOGIN;
		
		//logica!
		try {
			User user = this.userService.getUserByUsername(this.username);
			
			//reto para casa! implementar BCRYPT
			
			if(user != null && user.getPassword().equals(this.password)) {
				
				this.usuarioBean.setUsuario(user);
				
				String[] roles = this.userService.findRoles()
						.stream()
						.map(r -> r.getRole())
						.collect(Collectors.toList())
						.toArray(new String[] {});
				
				this.usuarioBean.setRoles(roles);
				
				target = PagesEnums.LOGIN_SUCCESS;
			}else {
				setError("Usuario/Password invalido");	
			}
		} catch (ServiceException e) {
			setError(e.getMessage());
		}
		
		return target.getPage();
	}
	
	public String irALogin() {	
		return "login-success";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
}
