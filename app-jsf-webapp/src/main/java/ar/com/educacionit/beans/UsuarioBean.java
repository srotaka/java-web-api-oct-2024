package ar.com.educacionit.beans;

import java.io.Serializable;
import java.util.stream.Collectors;

import ar.com.educacionit.beans.enums.PagesEnums;
import ar.com.educacionit.domain.User;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named
@SessionScoped
public class UsuarioBean implements Serializable{

	private static final long serialVersionUID = -5542155117157234481L;
	
	private User usuario;
	private String[] roles;
	
	public boolean logueado() {
		return this.usuario != null;
	}
	
	public String logout() {
		setUsuario(null);
		setRoles(new String[] {});
		return PagesEnums.LOGIN.getPage();
	}
	
	public String[] getUserRoles() {
		return this.usuario.getRoles()
				.stream()
				.map(role -> role.getRole())
				.collect(Collectors.toSet())
				.toArray(new String[]{});
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}
	public String[] getRoles() {
		return roles;
	}
	public void setRoles(String[] roles) {
		this.roles = roles;
	}
	
}
