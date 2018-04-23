package br.org.pmc.model.entity.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.org.pmc.model.entity.IEntity;

@Entity
public class Secretaria implements IEntity {

	private Long id;
	private String nome;
	private Boolean status;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	@Column(nullable = false)
	public String getNome() {
		return nome;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public boolean validateEmptyFields() {
		if (getNome() == null || getNome().trim().equals("")) {
			return false;
		}else {
			return true;
		}

	}

}
