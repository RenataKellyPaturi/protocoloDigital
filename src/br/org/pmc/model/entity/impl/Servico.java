package br.org.pmc.model.entity.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.org.pmc.model.entity.IEntity;


@Entity
public class Servico implements IEntity {

	private Long id;
	private boolean status;
	private String nome;
	private Float valor;
	private Boolean servicoUrb;

	public Servico() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	@Column(nullable = false)
	public String getNome() {
		return nome;
	}

	public boolean getStatus() {
		return status;
	}

	public Float getValor() {
		return valor;
	}

	public Boolean getServicoUrb() {
		return servicoUrb;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	public void setServicoUrb(Boolean servicoUrb) {
		this.servicoUrb = servicoUrb;
	}

	@Override
	public String toString() {
		return "Servico [id=" + id + ", status=" + status + ", nome=" + nome + ", valor=" + valor + ", servicoUrb="
				+ servicoUrb + "]";
	}

	public boolean validateEmptyFields() {
		if (getNome() == null || getNome().trim().equals("")) {
			return false;
		}else{
		return true;
		}
	}

}
