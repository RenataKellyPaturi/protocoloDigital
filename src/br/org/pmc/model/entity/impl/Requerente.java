package br.org.pmc.model.entity.impl;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import br.org.pmc.model.entity.IEntity;

@Entity
public class Requerente implements IEntity {
	private Long id;
	private String nome;
	private String matricula;
	private String email;
	private Secretaria secretaria;
	private String contato;
	private String cnpj;
	private String cpf;
	private Endereco endereco;
	private Boolean status;

	@OneToOne
	@JoinColumn(name = "id_secretaria")
	public Secretaria getSecretaria() {
		return secretaria;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getMatricula() {
		return matricula;
	}

	public String getEmail() {
		return email;
	}

	public String getContato() {
		return contato;
	}

	public String getCnpj() {
		return cnpj;
	}

	public String getCpf() {
		return cpf;
	}
	@Embedded
	public Endereco getEndereco() {
		return endereco;
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

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSecretaria(Secretaria secretaria) {
		this.secretaria = secretaria;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Requerente [id=" + id + ", nome=" + nome + ", matricula=" + matricula + ", email=" + email
				+ ", secretaria=" + secretaria + ", contato=" + contato + ", cnpj=" + cnpj + ", cpf=" + cpf
				+ ", endereco=" + endereco + "]";
	}

	@Override
	public boolean validateEmptyFields() {
		if(getNome() == null || getNome().trim().equals("")){
			return false;
		}else{
			return true;
		}
		
	}
	


}
