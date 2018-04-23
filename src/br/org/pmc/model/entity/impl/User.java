package br.org.pmc.model.entity.impl;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import br.org.pmc.model.entity.IEntity;
import br.org.pmc.model.entity.IUser;

@Entity
public class User implements IEntity, IUser {

	@Id
	@Column(nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String matricula;
	
	private String password;
	
	private String name;

	private String email;

	@Column(nullable = false, unique = true)
	private String cpf;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private List<LogDeAtividade> logsDeAtividades;
	
	public List<LogDeAtividade> getLogsDeAtividades() {
		return logsDeAtividades;
	}

	public void setLogsDeAtividades(List<LogDeAtividade> logsDeAtividades) {
		this.logsDeAtividades = logsDeAtividades;
	}

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "users")
	private List<UserProfile> userProfiles;

	public List<UserProfile> getUserProfiles() {
		return userProfiles;
	}

	public void setUserProfiles(List<UserProfile> userProfiles) {
		this.userProfiles = userProfiles;
	}

	private boolean status;
	
	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", email=" + email + ", matricula=" + id
				+ ", cpf=" + cpf + ", status=" + status + "]";
	}

	@Override
	public boolean validateEmptyFields() {
		if (getEmail() == null || getEmail().trim().equals("")) {
			return false;
		} else if (getName() == null || getName().trim().equals("")) {
			return false;
		} else if (getId() == null) {
			return false;
		} else if (getCpf() == null || getCpf().trim().equals("")) {
			return false;
		}
		return true;
	}
}