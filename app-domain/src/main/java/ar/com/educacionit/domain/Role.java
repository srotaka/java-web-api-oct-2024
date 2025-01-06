package ar.com.educacionit.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="role")
public class Role {

	@Id
	private Long id;
	
	@Column(name="role")
	private String role;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	//alt+shit+s
	@Override
	public String toString() {
		return "Role [id=" + id + ", role=" + role + "]";
	}
	
}
